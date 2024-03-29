package com.sinata.hi_library.restful

import com.sinata.hi_library.restful.annotation.*
import java.lang.reflect.Method
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type


class MethodParser(val baseUrl: String, method: Method) {

    private var replaceRelativeUrl: String? = null
    private var cacheStrategy: Int = CacheStrategy.NET_ONLY
    private var domainUrl: String? = null
    private var postForm: Boolean = true
    private var httpMethod: Int = HiRequest.METHOD.POST
    private var relativeUrl: String? = null
    private var returnType: Type? = null
    private var headers: MutableMap<String, String> = mutableMapOf()
    private var parameters: MutableMap<String, String> = mutableMapOf()

    init {
        //parse method annotations such as get,post baseurl
        parseMethodAnnotations(method)
        //parse method parameters such as path field
//        parseMethodParameters(method, args)
        //parse method generic return type
        parseMethodReturnType(method)
    }


    private fun parseMethodAnnotations(method: Method) {
        val annotations = method.annotations
        for (annotation in annotations) {
            when (annotation) {
                is GET -> {
                    relativeUrl = annotation.value
                    httpMethod = HiRequest.METHOD.GET
                }
                is POST -> {
                    relativeUrl = annotation.value
                    httpMethod = HiRequest.METHOD.POST
                    postForm = annotation.postForm
                }

                is PUT -> {
                    postForm = annotation.formPost
                    httpMethod = HiRequest.METHOD.PUT
                    relativeUrl = annotation.value
                }

                is DELETE -> {
                    httpMethod = HiRequest.METHOD.DELETE
                    relativeUrl = annotation.value
                }

                is Headers -> {
                    val headersArray = annotation.value
                    for (header in headersArray) {
                        val colon = header.indexOf(":")
                        check(colon == 0 || colon == -1) {
                            String.format(
                                "@header value must be in the form [name:value],but found [%s]",
                                header
                            )
                        }
                        val name = header.substring(0, colon)
                        val value = header.substring(colon + 1).trim()
                        headers[name] = value
                    }
                }
                is BaseUrl -> {
                    domainUrl = annotation.value
                }

                is CacheStrategy -> {
                    cacheStrategy = annotation.value
                }

                else -> {
                    throw IllegalStateException("cannot handle method annotation:" + annotation.javaClass.toString())
                }
            }
        }

        require(httpMethod == HiRequest.METHOD.GET || httpMethod == HiRequest.METHOD.POST) {
            String.format("method %s  must has one of GET,POST", method.name)
        }

        if (domainUrl == null) {
            domainUrl = baseUrl
        }
    }


    private fun parseMethodParameters(method: Method, args: Array<Any>) {
        //fun listCities(@Path("province")province:Int,@Field("page")page:Int):HiCall<JsonObject>
        val parameterAnnotations = method.parameterAnnotations
        val equal = parameterAnnotations.size == args.size
        require(equal) {
            String.format(
                "arguments annotations count %s don`t match expect count %s",
                parameterAnnotations.size,
                args.size
            )
        }

        //args
        for (index in args.indices) {
            val annotations = parameterAnnotations[index]
            require(annotations.size <= 1) {
                String.format("field can only has one annotation: index = $index")
            }
            val value = args[index]
            require(isPrimitive(value)) {
                "8 basic type are supported for now,index = $index"
            }

            val annotation = annotations[0]
            if (annotation is Filed) {
                val key = annotation.value
                parameters[key] = value.toString()
            } else if (annotation is Path) {
                val replaceName = annotation.value
                val replacement = value.toString()
                if (replaceName != null && replacement != null) {
                    replaceRelativeUrl = relativeUrl?.replace(replaceName, replacement)
                }
            } else if (annotation is CacheStrategy) {
                cacheStrategy = value as Int

            } else {
                throw IllegalStateException("cannot handle parameters annotation : " + annotation.javaClass.toString())
            }
        }
    }


    private fun isPrimitive(value: Any): Boolean {

        //String
        if (value.javaClass == String::class.java) {
            return true
        }

        try {
            //基本数据类型
            val field = value.javaClass.getField("TYPE")
            val clazz = field[null] as Class<*>
            if (clazz.isPrimitive) {
                return true
            }
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()

        }
        return false
    }

    private fun parseMethodReturnType(method: Method) {
        if (method.returnType != HiCall::class.java) {
            throw IllegalStateException(
                String.format(
                    "method %s must be type of HiCall.class",
                    method.name
                )
            )
        }
        val genericReturnType = method.genericReturnType
        if (genericReturnType is ParameterizedType) {
            val actualTypeArguments = genericReturnType.actualTypeArguments
            require(actualTypeArguments.size == 1) { "method %s can only has one generic return type" }
            returnType = actualTypeArguments[0]
        } else {
            throw IllegalStateException(
                String.format(
                    "method %s must has one generic return type",
                    method.name
                )
            )
        }
    }

    fun newRequest(method: Method, args: Array<out Any>?): HiRequest {
        val arguments: Array<Any> = args as Array<Any>? ?: arrayOf()
        parseMethodParameters(method, arguments)
        val hiRequest = HiRequest()
        hiRequest.domainUrl = domainUrl
        hiRequest.formPost = postForm
        hiRequest.headers = headers
        hiRequest.httpMethod = httpMethod
        hiRequest.relativeUrl = replaceRelativeUrl ?: relativeUrl
        hiRequest.parameters = parameters
        hiRequest.returnType = returnType
        hiRequest.cacheStrategy = cacheStrategy
        return hiRequest
    }

    companion object {
        fun parse(baseUrl: String, method: Method): MethodParser {
            return MethodParser(baseUrl, method)
        }
    }
}