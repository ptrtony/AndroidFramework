package com.sinata.common.city

import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.GET

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/26
 */
internal interface CityApi {
    @GET("cities")
    fun listCitys():HiCall<CityModel>
}