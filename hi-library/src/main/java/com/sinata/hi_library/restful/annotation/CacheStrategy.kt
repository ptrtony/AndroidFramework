package com.sinata.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/5
 */

@Target(AnnotationTarget.FUNCTION,AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class CacheStrategy(val value: Int = NET_ONLY) {
    companion object{
        const val CACHE_FIRST = 1 // 请求接口时候先读取本地缓存，再读取接口，接口成功后更新缓存（页面初始化数据）
        const val NET_ONLY = 2 // 仅仅只请求接口 （一般是分页和独立非列表页）
        const val NET_CACHE = 3 //先接口，接口成功后更新缓存（一般是下拉更新）
    }
}