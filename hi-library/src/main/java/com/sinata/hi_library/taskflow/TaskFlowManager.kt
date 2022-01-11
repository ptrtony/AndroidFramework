package com.sinata.hi_library.taskflow

import android.os.Looper
import androidx.annotation.MainThread
import java.lang.Exception


/**
 * 对taskRuntime的包装，对外暴露的类，用于启动任务
 */
object TaskFlowManager {
    fun addBlockTask(taskId:String):TaskFlowManager{
        TaskRuntime.addBlockTask(taskId)
        return this
    }

    fun addBlockTasks(vararg taskIds:String):TaskFlowManager{
        TaskRuntime.addBlockTasks(*taskIds)
        return this
    }

    //project任务组 ，也有可能是独立的任务组
    @MainThread
    fun start(task:Task){
        assert(Thread.currentThread() == Looper.getMainLooper().thread){
            "start method must be invoke on mainThread"
        }
        val startTask = if (task is Project) task.startTask else task
        startTask.start()

        while (TaskRuntime.hasBlockTasks()){
            try {
                Thread.sleep(10)
            }catch (e:Exception){

            }

            //主线程唤醒之后，存在等待队列的任务
            //那么让等待队列中的执行任务
            while (TaskRuntime.hasWaitingTasks()){
                TaskRuntime.runWaitingTasks()
            }
        }
    }

}