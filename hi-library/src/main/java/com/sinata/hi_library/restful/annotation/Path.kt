package com.sinata.hi_library.restful.annotation

/**
 * @GET("https://api.devio.org/as/{province}")
 * fun test @Path("province") int provinceId
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
annotation class Path(val value:String)
