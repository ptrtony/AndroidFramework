package com.sinata.framework.http.api

import com.sinata.framework.model.HomeModel
import com.sinata.framework.model.TabCategory
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.Filed
import com.sinata.hi_library.restful.annotation.GET
import com.sinata.hi_library.restful.annotation.Path

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 4/8/2021
 */
interface HomeApi {

    @GET("category/categories")
    fun queryTabList(): HiCall<List<TabCategory>>

    @GET("home/{categoryId}")
    fun queryTabCategoryList(
        @Path("categoryId") categoryId: String,
        @Filed("pageIndex") pageIndex: Int,
        @Filed("pageSize") pageSize: Int
    ): HiCall<HomeModel>
}

