package com.sinata.framework.http.api
import com.sinata.framework.model.DetailModel
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.GET
import com.sinata.hi_library.restful.annotation.Path

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/14
 */


interface DetailApi {

    @GET("goods/detail/{id}")
    fun queryDetail(@Path("id")goodsId:String):HiCall<DetailModel>


}