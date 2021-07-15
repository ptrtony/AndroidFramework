package com.sinata.hi_library.restful.annotation

import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Retention

/**
 * @BaseUrl("https://api.devio.org/as/")
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(RetentionPolicy.RUNTIME)
annotation class BaseUrl(val value: String)
