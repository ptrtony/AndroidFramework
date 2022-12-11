package com.sinata.hi_library.taskflow

import android.util.SparseArray

/**

Title: 用于记录每一个task实例的 运行时的信息的封装
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/10
 */
class TaskRuntimeInfo(val task:Task){
    val stateTime = SparseArray<Long>()
    var isBlockTask = false
    var threadName:String?=null

    fun setStateTime(@TaskState state:Int,time:Long){
        stateTime.put(state,time)
    }

    fun isSameTask(task: Task?):Boolean{
        return task != null && this.task === task
    }

    override fun toString(): String {
        return "TaskRuntimeInfo(task=$task, stateTime=$stateTime, isBlockTask=$isBlockTask, threadName=$threadName)"
    }


}