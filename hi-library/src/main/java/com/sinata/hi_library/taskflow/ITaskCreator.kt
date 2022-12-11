package com.sinata.hi_library.taskflow

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/10
 */
interface ITaskCreator {
    fun createTask(taskName:String):Task
}