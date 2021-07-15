package com.sinata.hi_library.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * @Header({"connection:keep-alive","auth-token:token"})
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class Headers(vararg val value: String)
