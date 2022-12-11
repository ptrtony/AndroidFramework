package com.sinata.hi_library.taskflow

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/10
 */
interface TaskListener {
    fun onStart(task: Task)

    fun onRunning(task: Task)

    fun onFinished(task: Task)
}