package com.sinata.hi_library.restful

import androidx.annotation.IntDef
import com.sinata.hi_library.restful.annotation.BaseUrl
import com.sinata.hi_library.restful.annotation.GET
import java.lang.reflect.Type

open class HiRequest {


    @METHOD
    var httpMethod = 0 // 请求方式GET、POST
    var formPost = true // 是否表单提交，POST有效

    //    var baseUrl:BaseUrl?=null // 域名
    var headers: MutableMap<String, String>? = null // 请求头
    var parameters: MutableMap<String, String>? = null // 请求入参
    var domainUrl: String? = null//域名
    var relativeUrl: String? = null//相对路径
    var returnType: Type? = null

    @IntDef(value = [METHOD.GET, METHOD.POST])
    annotation class METHOD {
        companion object {
            const val GET = 0
            const val POST = 1
        }
    }

    //返回的是请求的完整的url
    fun endPointUrl(): String? {
        if (relativeUrl == null) {
            throw IllegalStateException("relative url must not be null")
        }
        if (!relativeUrl!!.startsWith("/")) {
            return domainUrl + relativeUrl
        }
        val indexOf = domainUrl!!.indexOf("/")
        return domainUrl!!.substring(0, indexOf) + relativeUrl
    }

    fun addHeader(name: String, value: String) {
        if (headers == null){
            headers = mutableMapOf()
        }
        headers!![name] = value
    }
}
