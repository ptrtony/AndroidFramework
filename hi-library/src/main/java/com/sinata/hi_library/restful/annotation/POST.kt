package com.sinata.hi_library.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * @POST("https://api.devio.org/as/")
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class POST(val value:String,val postForm:Boolean)
