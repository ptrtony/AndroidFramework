package com.sinata.hi_library.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @GET("https://api.devio.org/as/")
 * fun test @Field("province") int provinceId
 */



@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class GET(val value: String)
