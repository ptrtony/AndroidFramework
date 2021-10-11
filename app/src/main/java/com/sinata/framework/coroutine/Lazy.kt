package com.sinata.framework.coroutine

import kotlin.properties.Delegates

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/8
 */
class Lazy {
    var a:String by Delegates.observable("默认值 ") { property, oldValue, newValue ->
        println("$oldValue -> $newValue")
    }
}

fun main(){
    val lazy = Lazy()
    lazy.a = "第一次修改"
    lazy.a = "第二次修改"
}