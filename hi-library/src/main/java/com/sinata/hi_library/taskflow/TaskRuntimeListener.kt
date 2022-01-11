package com.sinata.hi_library.taskflow

import android.os.Build
import android.util.Log
import rx.android.BuildConfig
import java.lang.StringBuilder

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2022/1/10
 */
class TaskRuntimeListener : TaskListener {
    override fun onStart(task: Task) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, task.id + START_METHOD)
        }
    }

    override fun onRunning(task: Task) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, task.id + RUNNING_METHOD)
        }
    }

    override fun onFinished(task: Task) {
        if (BuildConfig.DEBUG) {
            Log.e(TAG, task.id + FINISHED_METHOD)
        }

        logTaskRuntimeInfo(task)
    }

    private fun logTaskRuntimeInfo(task: Task) {
        val taskRuntimeInfo = TaskRuntime.getTaskRuntimeInfo(task.id) ?: return
        val startTime = taskRuntimeInfo.stateTime[TaskState.START]
        val runningTime = taskRuntimeInfo.stateTime[TaskState.RUNNING]
        val finishedTime = taskRuntimeInfo.stateTime[TaskState.FINISHED]

        val builder = StringBuilder()
        builder.append(WRAPPER)
            .append(TAG)
            .append(WRAPPER)
            .append(WRAPPER)
            .append(HALF_LINE)
            .append(if (task is Project) "project" else "task${task.id}")
            .append(FINISHED_METHOD)
            .append(HALF_LINE)

        addTaskInfoLineInfo(builder, DEPENDENCIES,getTaskDependencyInfo(task))
        addTaskInfoLineInfo(builder, IS_BLOCK_TASK,taskRuntimeInfo.isBlockTask.toString())
        addTaskInfoLineInfo(builder, THREAD_NAME,taskRuntimeInfo.threadName!!)
        addTaskInfoLineInfo(builder, START_TIME, "$startTime ms")
        addTaskInfoLineInfo(builder, WAITING_TIME,"${runningTime - startTime} ms")
        addTaskInfoLineInfo(builder, TASK_CONSUME,"${finishedTime - runningTime} ms")
        addTaskInfoLineInfo(builder, FINISHED_TIME,"${finishedTime} ms")
        builder.append(HALF_LINE)
        builder.append(HALF_LINE)
        builder.append(WRAPPER)
        if (BuildConfig.DEBUG){
            Log.e(TAG,builder.toString())
        }
    }

    private fun addTaskInfoLineInfo(
        builder: StringBuilder,
        key: String,
        value: String
    ) {
        builder.append("| $key : $value")
    }

    private fun getTaskDependencyInfo(task: Task): String {
        val stringBuilder = StringBuilder()
        for (s in task.dependTasksName){
            stringBuilder.append("$s  ")
        }
        return stringBuilder.toString()
    }

    companion object {
        const val TAG: String = "TaskFlow"
        const val START_METHOD = "-- onStart --"
        const val RUNNING_METHOD = "-- onRunning --"
        const val FINISHED_METHOD = "-- onFinished --"

        const val DEPENDENCIES = "依赖任务"
        const val THREAD_NAME = "线程名称"
        const val START_TIME = "开始执行时间"
        const val WAITING_TIME = "等待执行时间耗时"
        const val TASK_CONSUME = "任务执行耗时"
        const val IS_BLOCK_TASK = "是否是阻塞任务"
        const val FINISHED_TIME = "任务结束时刻"
        const val WRAPPER = "\n"
        const val HALF_LINE = "=============="

    }

}