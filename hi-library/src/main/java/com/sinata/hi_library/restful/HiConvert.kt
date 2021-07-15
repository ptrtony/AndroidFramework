package com.sinata.hi_library.restful

import java.lang.reflect.Type

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 14/7/2021
 */
interface HiConvert {
    fun <T> convert(rawData:String,dataType:Type):HiResponse<T>
}