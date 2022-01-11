package com.sinata.hi_library.taskflow

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2022/1/10
 */
interface ITaskCreator {
    fun createTask(taskName:String):Task
}