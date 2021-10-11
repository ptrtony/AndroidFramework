package com.sinata.framework.design.singleton

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/7
 */
class Singleton4Kotlin {
    companion object{
        val instance: Singleton4Kotlin = SingleProvider.holder
    }

    private object SingleProvider{
        val holder = Singleton4Kotlin()
    }
}