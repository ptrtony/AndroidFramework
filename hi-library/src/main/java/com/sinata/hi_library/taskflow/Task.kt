package com.sinata.hi_library.taskflow

import androidx.core.os.TraceCompat
import com.sinata.hi_library.taskflow.TaskRuntime.executeTask
import java.lang.RuntimeException
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:
@author jingqiang.cheng
@date 2022/1/10
 */

abstract class Task @JvmOverloads constructor(
    val id: String
    /**任务名称**/
    ,
    var isAsyncTask: Boolean = false
    /**是否是异步任务**/
    ,
    var delayMills: Long = 0
    /**延迟执行的时间**/
    ,
    internal var priority: Int = 0
    /**执行的优先级**/

) : Runnable, Comparable<Task> {

    var executeTime: Long = 0L
        //执行时间
        protected set
    var state: Int = TaskState.IDLE
        protected set

    val dependTasks: MutableList<Task> =
        ArrayList()//当前task 依赖了那些前置任务，只有当dependTasks集合中的所有的任务都执行完，当前的任务才执行
    val behindTasks: MutableList<Task> =
        ArrayList()//当前task 被后置任务依赖，只有当当前这个task执行完，bindTasks集合中的后置任务才可以执行

    private val taskListeners: MutableList<TaskListener> = ArrayList() //任务运行监听器集合
    private var taskRuntimeListener: TaskRuntimeListener? = TaskRuntimeListener() //用户输出的task 运行时的日志
    val dependTasksName: MutableList<String> =
        ArrayList() //用于运行时log 统计输出 输出当前的task 依赖了那些前置任务，

    //用于存储这些前置任务的名称


    fun addTaskListener(taskListener: TaskListener) {
        if (!taskListeners.contains(taskListener)) {
            taskListeners.add(taskListener)
        }
    }

    open fun start() {
        if (state != TaskState.IDLE) {
            throw RuntimeException("cannot run task $id again")
        }
        onStart()
        executeTime = System.currentTimeMillis()
        //执行当前任务
        executeTask(this)
    }

    private fun onStart() {
        state = TaskState.START
        TaskRuntime.setStateInfo(this)
        for (listener in taskListeners) {
            listener.onStart(this)
        }
        taskRuntimeListener?.onStart(this)
    }

    override fun run() {
        //改变任务的状态--onStart onRunning onFinished --通知后置任务去开始执行
        TraceCompat.beginSection(id)
        onRunning()
        run(id)
        onFinished()
        //后置任务去执行
        notifyBehindTasks()
        recycle()
        TraceCompat.endSection()


    }


    //回收当前task的一些数据
    private fun recycle() {
        dependTasks.clear()
        behindTasks.clear()
        taskListeners.clear()
        taskRuntimeListener = null
    }


    private fun onRunning() {
        state = TaskState.RUNNING
        TaskRuntime.setStateInfo(this)
        TaskRuntime.setThreadName(this,Thread.currentThread().name)
        for (taskListener in taskListeners) {
            taskListener.onRunning(this)
        }
        taskRuntimeListener?.onRunning(this)
    }

    private fun onFinished() {
        state = TaskState.FINISHED
        TaskRuntime.setStateInfo(this)
        TaskRuntime.removeBlockTask(this.id)
        for (taskListener in taskListeners) {
            taskListener.onFinished(this)
        }
        taskRuntimeListener?.onFinished(this)
    }

    private fun notifyBehindTasks() {
        //通知后置任务尝试执行
        if (behindTasks.isNotEmpty()) {
            if (behindTasks.size > 1) {
                Collections.sort(behindTasks, TaskRuntime.taskComparator)
            }

            //遍历behindTask的后置任务，通知他们，告诉他们你的一个前置依赖已经执行完成了
            for (behindTask in behindTasks) {
                // A bindTasks->(B,C) A执行完后，B、C才能够被执行
                behindTask.dependTaskFinished(this)
            }
        }
    }

    private fun dependTaskFinished(dependTask: Task) {
        // A bindTasks->(B,C) A执行完后，B、C才能够被执行
        // task -> B,C ，，dependTask=A
        if (dependTasks.isEmpty()) {
            return
        }
        //把A从B、C的前置依赖任务，集合中移除
        dependTasks.remove(dependTask)

        //B、C中的前置任务是否都已经移除完
        if (dependTasks.isEmpty()) {
            start()
        }
    }

    /**
     * 给当前task添加一个前置的依赖
     */
    open fun dependOn(dependTask: Task) {
        var task = dependTask
        if (this != task) {
            if (dependTask is Project) {
                task = dependTask.endTask
            }
            dependTasks.add(task)
            dependTasksName.add(task.id)
            //当前task 依赖了dependTask,那么我们还需要把dependTask-里面的behindTask添加到当前的task里面
            if (!task.dependTasks.contains(this)) {
                task.dependTasks.add(this)
            }
        }
    }


    open fun removeDependence(dependTask: Task) {
        var task = dependTask
        if (dependTask != this) {
            if (dependTask is Project) {
                task = dependTask.endTask
            }
            dependTasks.remove(task)
            dependTasksName.remove(task.id)

            //把当前task 从dependTask的后置任务集合behindTask中移除
            //达到解除两个任务的依赖关系
            if (task.behindTasks.contains(this)) {
                task.behindTasks.remove(this)
            }

        }
    }

    /**
     * 给当前的task移除一个后置的任务
     */
    open fun removeBehind(behindTask: Task) {
        if (behindTask != this) {
            behindTasks.remove(behindTask)
            behindTask.removeDependence(this)
        }
    }


    /**
     * 给当前任务添加后置任务
     */
    open fun behind(behindTask: Task) {
        var task = behindTask

        if (this != behindTask) {
            if (behindTask is Project){
                task = behindTask.startTask
            }
            behindTasks.add(task)
            //把当前的task添加到behindTask的前面
            task.dependOn(this)
        }
    }


    abstract fun run(id: String)

    override fun compareTo(other: Task): Int {
        return Utils.compareTask(this, other)
    }

    class Builder(projectName: String, iTaskCreator: ITaskCreator) {
        private val mTaskFactory = TaskFactory(iTaskCreator)
        private val mStartTask: Task = Project.CriticalTask(projectName + "_start")
        private val mEndTask: Task = Project.CriticalTask(projectName + "_end")
        private val mProject: Project = Project(projectName)
        private var mPriority: Int = 0 //默认为该任务组中，所有任务优先级最高的
        private var mCurrentTaskShouldDependOnStartTask = true//本次添加进来的这个task是否把start节点当作依赖

        //那如果这个task他存在与其他的task的依赖关系，那么就不能直接添加到start节点的后面了，而需要通过dependOn的关系来指定任务关系
        private var mCurrentAddTask: Task? = null
        fun add(id: String): Builder {
            val task = mTaskFactory.getTask(id)
            if (task.priority > mPriority) {
                mPriority = task.priority
            }
            return add(task)
        }

        private fun add(task: Task): Builder {
            if (mCurrentTaskShouldDependOnStartTask && mCurrentAddTask != null) {
                mStartTask.behind(task)
            }
            mCurrentAddTask = task
            mCurrentTaskShouldDependOnStartTask = true
            mCurrentAddTask!!.behind(task)
            return this
        }

        fun dependOn(id: String): Builder {
            return dependOn(mTaskFactory.getTask(id))
        }

        private fun dependOn(task: Task): Builder {
            //确定 刚才我们所添加进来的mCurrentAddTask 和 task的依赖关系 mCurrentAddTask依赖于task
            task.behind(mCurrentAddTask!!)
            //stark --task10 --mCurrentAddTask(task11) --end
            mCurrentTaskShouldDependOnStartTask = false
            mEndTask.removeDependence(task)
            return this
        }


        fun build(): Project {
            if (mCurrentAddTask == null) {
                mStartTask.behind(mEndTask)
            } else {
                if (mCurrentTaskShouldDependOnStartTask) {
                    mEndTask.behind(mCurrentAddTask!!)
                }
            }

            mStartTask.priority = mPriority
            mEndTask.priority = mPriority
            mProject.startTask = mStartTask
            mProject.endTask = mEndTask
            return mProject
        }
    }


    private class TaskFactory(private val iTaskCreator: ITaskCreator) {
        //利用iTaskCreator创建task实例，并管理
        private val mCacheTasks: MutableMap<String, Task> = HashMap()

        fun getTask(id: String): Task {
            var task = mCacheTasks[id]
            if (task != null) {
                return task
            }
            task = iTaskCreator.createTask(id)
            requireNotNull(task) { "create task fail,make sure iTaskCreator can create a task with only taskId" }
            mCacheTasks[id] = task
            return task
        }
    }


}