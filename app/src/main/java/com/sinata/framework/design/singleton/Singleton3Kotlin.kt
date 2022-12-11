package com.sinata.framework.design.singleton

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/7
 */
class Singleton3Kotlin private constructor(){
    companion object {
        val INSTANCE: Singleton3Kotlin by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            Singleton3Kotlin()
        }
    }
}