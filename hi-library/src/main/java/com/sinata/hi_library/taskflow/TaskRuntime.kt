package com.sinata.hi_library.taskflow

import android.text.TextUtils
import android.util.Log
import com.sinata.hi_library.executor.HiExecutor
import com.sinata.hi_library.utils.MainHandler
import rx.android.BuildConfig
import java.lang.StringBuilder
import java.util.*
import java.util.logging.Handler
import kotlin.Comparator
import kotlin.collections.HashMap

/**
 *  taskflow 运行时的任务调度器
 *  1、根据task属性以不同的策略(线程、同步、延迟)调度任务
 *  2、校验 依赖树中是否存在唤醒依赖
 *  3、校验依赖树中是否存在taskId的任务
 *  4、统计所有task的运行时信息(线程、状态、开始执行时间、耗时时间、是否是阻塞任务)，用于log输出
 */

internal object TaskRuntime {
    //通过addBlockTask(String name)指定启动阶段 需要阻塞完成的任务，只有当blockTaskId当中的任务执行完后
    //才会释放application的阻塞 才会拉起LaunchActivity
    val blockTasksId:MutableList<String> = mutableListOf()

    //如果blockTaskId集合中的任务还没有完成 那么在主线程中执行的任务会被添加到waitingTasks集合中去，
    //目的是为了优先保证阻塞任务的优先完成，尽可能早的拉起LaunchActivity
    val waitingTasks:MutableList<Task> = mutableListOf()

    //记录下启动阶段所有的任务的运行时信息key是taskId
    val taskRuntimeInfos: MutableMap<String,TaskRuntimeInfo> = HashMap()

    val taskComparator = Comparator<Task> { o1, o2 -> Utils.compareTask(o1,o2) }
    @JvmStatic
    fun addBlockTask(id:String){
        if (!TextUtils.isEmpty(id)){
            blockTasksId.add(id)
        }
    }

    @JvmStatic
    fun addBlockTasks(vararg ids:String){
        if (ids.isNotEmpty()){
            for (id in ids){
                blockTasksId.add(id)
            }
        }
    }

    @JvmStatic
    fun removeBlockTask(id:String){
        blockTasksId.remove(id)
    }

    @JvmStatic
    fun removeBlockTask(vararg ids:String){
        if (ids.isNotEmpty()){
            for(id in ids){
                blockTasksId.remove(id)
            }
        }
    }

    @JvmStatic
    fun hasBlockTasks():Boolean{
        return blockTasksId.iterator().hasNext()
    }

    @JvmStatic
    fun hasWaitingTasks():Boolean{
        return waitingTasks.iterator().hasNext()
    }

    @JvmStatic
    fun setThreadName(task: Task,threadName:String){
        val taskRuntimeInfo = getTaskRuntimeInfo(task.id)
        if (taskRuntimeInfo != null){
            taskRuntimeInfo.threadName = threadName
        }
    }

    @JvmStatic
    fun setStateInfo(task: Task){
        val taskRuntimeInfo = getTaskRuntimeInfo(task.id)
        taskRuntimeInfo?.setStateTime(task.state,System.currentTimeMillis())
    }

    @JvmStatic
    fun getTaskRuntimeInfo(id: String): TaskRuntimeInfo? {
        return taskRuntimeInfos[id]
    }
    //根据task的属性以不同的策略 调度task
    @JvmStatic
    fun executeTask(task: Task){
        if (task.isAsyncTask){
            HiExecutor.executor(runnable = task)
        }else{
            //else 里面的都是主线程 执行的
            //延迟执行 但是如果这个延迟任务 它存在后置任务 A(延迟任务) --> B -->C(Block task)
            if (task.delayMills > 0 && hasBlockBehindTask(task)) {
                MainHandler.postDelayed(task,task.delayMills)
                return
            }

            if (!hasBlockTasks()){
                task.run()
            }else{
                addWaitingTask(task)
            }
        }
    }

    //把一个主线程上需要执行的任务，但又不影响LaunchActivity的启动，添加到队列中去
    private fun addWaitingTask(task: Task) {
        if (!waitingTasks.contains(task)){
            waitingTasks.add(task)
        }
    }

    //检测一个延迟任务 是否存在后置的 阻塞任务（就是等他们都执行完了，才会释放application的阻塞，才会拉起LaunchActivity）
    private fun hasBlockBehindTask(task: Task): Boolean {
        if (task is Project.CriticalTask){
            return false
        }

        val behindTasks = task.behindTasks
        for (behindTask in behindTasks){
            //需要判断一个task是不是阻塞任务，blockTaskIds
            val behindTaskInfo = getTaskRuntimeInfo(behindTask.id)
            if (behindTaskInfo!= null && behindTaskInfo.isBlockTask){
                return true
            }else{
                hasBlockBehindTask(behindTask)
            }
        }
        return false
    }

    //验证 依赖树中是否存在环形依赖校验 ，依赖树中是否存在taskId项目的任务 初始化task 对应taskRuntimeInfo
    //遍历依赖树 完成启动前的检查和初始化
    @JvmStatic
    fun traversalDependencyTreeAndInit(task: Task){
        val traversalVisitor = linkedSetOf<Task>()
        traversalVisitor.add(task)
        innerTraversalDependencyTreeAndInit(task,traversalVisitor)
        val iterator = blockTasksId.iterator()
        while (iterator.hasNext()){
            val taskId = iterator.next()
            //检查这个阻塞任务 是否存在依赖树中
            if (!taskRuntimeInfos.containsKey(taskId)){
                throw RuntimeException("block task ${task.id} not in dependency tree.")
            }else{

            }
        }
    }

    private fun innerTraversalDependencyTreeAndInit(
        task: Task,
        traversalVisitor: LinkedHashSet<Task>
    ) {
        //初始化runtime信息
        var taskRuntimeInfo = getTaskRuntimeInfo(task.id)
        if (taskRuntimeInfo == null){
            taskRuntimeInfo = TaskRuntimeInfo(task)
            if (blockTasksId.contains(task.id)){
                taskRuntimeInfo.isBlockTask = true
            }
            taskRuntimeInfos[task.id] = taskRuntimeInfo
        }else{
            if (!taskRuntimeInfo.isSameTask(task)){
                throw RuntimeException("not allow to contain the same id${task.id}")
            }else{
                val task = getTaskRuntimeInfo(task.id)?.task
                traversalDependencyPriority(task)
            }

        }

        //校验环形依赖
        for (behindTask in task.behindTasks){
            if (!traversalVisitor.contains(behindTask)){
                traversalVisitor.add(behindTask)
            }else{
                throw RuntimeException("not allow loopback dependency,task id=${task.id}")
            }

            //start -->task1 -->task2-->task3-->task4--task5-->end
            //对task3后面的依赖任务路径上的task做环形任务的检查 初始化runtimeInfo
            if (BuildConfig.DEBUG && behindTask.behindTasks.isEmpty()){
                //behindTask is end
                val iterator = traversalVisitor.iterator()
                val builder:StringBuilder = StringBuilder()
                while (iterator.hasNext()){
                    builder.append(iterator.next().id)
                    builder.append("--------->")
                }
                Log.e(TaskRuntimeListener.TAG,builder.toString())
            }
            innerTraversalDependencyTreeAndInit(behindTask,traversalVisitor)
            traversalVisitor.remove(behindTask)
        }
    }

    private fun traversalDependencyPriority(task: Task?) {

    }

    @JvmStatic
    fun runWaitingTasks() {
        if (hasWaitingTasks()){
            if (waitingTasks.size > 1){
                Collections.sort(waitingTasks,taskComparator)
            }
            if (hasBlockTasks()){
                val head = waitingTasks.removeAt(0)
                head.start()
            }else{
                for (waitingTask in waitingTasks){
                    MainHandler.postDelayed(waitingTask,waitingTask.delayMills)
                }
                waitingTasks.clear()
            }
        }
    }

}