package com.sinata.hi_library.taskflow

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2022/1/10
 */
object Utils {
    /**
     *比较两个任务的执行顺序
     * 优先极越高的越先执行
     */
    fun compareTask(task1:Task,task2:Task):Int{
        if (task1.priority < task2.priority){
            return 1
        }

        if (task1.priority > task2.priority){
            return -1
        }

        return 0
    }
}