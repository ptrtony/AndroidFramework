package com.sinata.framework.flutter

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/11/25
 */
interface IHiBridge<P,Callback> {
    fun onBack(p:P)
    fun gotoNative(p:P)
    fun getHeaderParams(callback: Callback)
}