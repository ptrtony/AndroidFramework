package com.sinata.framework.http.api

import com.google.gson.JsonObject
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.Field
import com.sinata.hi_library.restful.annotation.GET

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 14/7/2021
 */
interface TestApi {

    @GET("citys")
    fun listCitys(@Field("name") name: String): HiCall<JsonObject>
}