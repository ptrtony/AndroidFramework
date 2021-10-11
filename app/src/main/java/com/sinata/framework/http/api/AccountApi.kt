package com.sinata.framework.http.api

import com.sinata.framework.model.CourseNotice
import com.sinata.framework.model.UserProfile
import com.sinata.hi_library.restful.HiCall
import com.sinata.hi_library.restful.annotation.Filed
import com.sinata.hi_library.restful.annotation.GET
import com.sinata.hi_library.restful.annotation.POST

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 14/7/2021
 */
interface AccountApi {

    @POST("user/login",postForm = true)
    fun login(@Filed(value = "username") username: String,@Filed("password")password:String): HiCall<String>

    @POST("user/registration")
    fun registration(
        @Filed("userName") userName:String,
        @Filed("password")password: String,
        @Filed("imoocId") imoocId:String,
        @Filed("orderId")orderId:String
    ):HiCall<String>

    @GET("user/profile")
    fun profile():HiCall<UserProfile>

    @GET("notice")
    fun notice():HiCall<CourseNotice>

}