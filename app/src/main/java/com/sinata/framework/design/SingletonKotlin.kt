package com.sinata.framework.design

/**

Title:
Description: 懒汉单例模式
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/28
 */
class SingletonKotlin private constructor() {
    companion object {
        private var instance: SingletonKotlin? = null
            get() {
                if (field == null) {
                    field = SingletonKotlin()
                }
                return field
            }


        @Synchronized
        fun get(): SingletonKotlin {
            return instance!!
        }
    }
}