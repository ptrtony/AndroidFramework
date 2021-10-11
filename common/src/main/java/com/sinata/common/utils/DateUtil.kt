package com.sinata.common.utils

import java.text.SimpleDateFormat
import java.util.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/8
 */
object DateUtil {
    private const val MD_FORMAT = "MM-dd"
    private const val DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss"

    fun getMDDate(date: Date): String {
        val sdf = SimpleDateFormat(MD_FORMAT,Locale.CHINA)
        return sdf.format(date)
    }

    fun getMDDate(dateString:String):String{
        if (dateString.isNullOrBlank())return ""
        val sdf = SimpleDateFormat(DEFAULT_FORMAT, Locale.CHINA)
        return getMDDate(sdf.parse(dateString))
    }
}