package com.sinata.hi_library.restful

/**
 * 响应报文
 */
class HiResponse<T> {

    companion object {
        var SUCCESS: Int = 0
    }

    var rawData: String? = null // 原始数据
    var code: Int = 0 // 业务状态码
    var data: T? = null // 业务数据
    var errorData: Map<String, String>? = null//错误状态下的数据
    var msg: String? = null //错误信息
}