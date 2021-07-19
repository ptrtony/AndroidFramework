package com.sinata.hi_library.restful.annotation


/**
 * @Header({"connection:keep-alive","auth-token:token"})
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Headers(vararg val value: String)
