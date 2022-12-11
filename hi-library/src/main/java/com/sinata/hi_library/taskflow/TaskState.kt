package com.sinata.hi_library.taskflow

import androidx.annotation.IntDef

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/10
 */

@Retention(AnnotationRetention.SOURCE)
@IntDef(TaskState.IDLE,TaskState.START,TaskState.RUNNING,TaskState.FINISHED)
annotation class TaskState{
    companion object{
        const val IDLE = 0 //静止
        const val START = 1 // 启动，可能需要等待调度
        const val RUNNING = 2 //执行
        const val FINISHED = 3 // 执行结束
    }
}
