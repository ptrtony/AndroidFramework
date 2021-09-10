package com.sinata.hi_library.restful

import java.lang.reflect.Type

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 14/7/2021
 */
interface HiConvert {
    fun <T> convert(rawData:String,dataType:Type):HiResponse<T>
}