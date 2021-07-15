package com.sinata.hi_library.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy

/**
 * @GET("https://api.devio.org/as/{province}")
 * fun test @Path("province") int provinceId
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Path(val value:String)
