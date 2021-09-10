package com.sinata.framework.http.api

import com.sinata.framework.model.SubCategory
import com.sinata.framework.model.TabCategory
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.GET
import com.sinata.hi_library.restful.annotation.Path

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 20/8/2021
 */
interface CategoryApi {

    @GET("category/categories")
    fun queryCategoryList():HiCall<List<TabCategory>>


    @GET("category/subcategories/{categoryId}")
    fun querySubcategoryList(@Path("categoryId")categoryId:String):HiCall<List<SubCategory>>
}