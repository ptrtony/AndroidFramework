package com.sinata.hi_library.restful.annotation

import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy


/**
 * @BaseUrl("https://api.devio.org/as/")
 * fun test @Field("province") int provinceId
 */
@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
annotation class Field(val value: String)
