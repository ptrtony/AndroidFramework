package com.sinata.framework.http.api

import com.sinata.framework.model.Favorite
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.POST
import com.sinata.hi_library.restful.annotation.Path

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/20
 */
interface FavoriteApi {

    @POST("/favorites/{goodsId}")
    fun favorite(@Path("goodsId")goodsId:String):HiCall<Favorite>
}