package com.sinata.framework.http.api

import com.sinata.framework.model.SubcategoryGoodsModel
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.Filed
import com.sinata.hi_library.restful.annotation.GET
import com.sinata.hi_library.restful.annotation.Path
import retrofit2.http.Field

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/8/2021
 */
interface GoodsApi {


    @GET("goods/goods/{categoryId}")
    fun queryCategoryGoodsList(
        @Path("categoryId") categoryId: String,
        @Filed("subcategoryId") subcategoryId: String,
        @Filed("pageSize") pageSize: Int,
        @Field("pageIndex") pageIndex: Int
    ): HiCall<SubcategoryGoodsModel>


}