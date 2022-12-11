package com.sinata.hi_library.taskflow

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/1/10
 */
class Project constructor(id: String) : Task(id) {

    lateinit var startTask: Task //任务组的开始节点
    lateinit var endTask: Task //任务组中所有的任务结束

    override fun start() {
        startTask.start()
    }
    override fun run(id: String) {
        //不需要处理的

    }

    override fun behind(behindTask: Task) {
       //当咱们给一个任务组添加后置任务的时候，那么这个任务应该添加到 组当中谁的后面
        endTask.behind(behindTask)//把新来的后置任务添加到任务组结束节点上面去，这样的话，任务组里面的任务都结束了，这个后置任务才能执行

    }

    override fun dependOn(dependTask: Task) {
        startTask.dependOn(dependTask)
    }

    override fun removeDependence(dependTask: Task) {
        startTask.removeDependence(dependTask)
    }

    override fun removeBehind(behindTask: Task) {
        endTask.removeBehind(behindTask)
    }

    internal class CriticalTask internal constructor(id: String) : Task(id) {
        override fun run(id: String) {
            //nothing to do
        }

    }

}