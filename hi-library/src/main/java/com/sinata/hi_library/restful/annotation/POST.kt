package com.sinata.hi_library.restful.annotation

/**
 * @POST("https://api.devio.org/as/")
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class POST(val value:String,val postForm:Boolean = true)
