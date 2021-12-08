package com.sinata.framework.fragment.search

import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.Filed
import com.sinata.hi_library.restful.annotation.POST

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/16
 */
interface SearchApi {
    @POST("xxxxxx")
    fun querySearchApi(@Filed("addd")add:String):HiCall<List<Keyword>>
}