package com.sinata.hi_library.restful.http

import com.google.gson.reflect.TypeToken
import com.sinata.hi_library.restful.*
import okhttp3.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.*
import java.lang.IllegalStateException

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 14/7/2021
 */
class RetrofitCallFactory constructor(val baseUrl: String) : HiCall.Factory {

    private var hiConvert: HiConvert
    private var apiService: ApiService

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .build()

        apiService = retrofit.create(ApiService::class.java)
        hiConvert = GsonConvert()
    }

    override fun newCall(request: HiRequest): HiCall<Any> {
        return RetrofitCall(request)
    }


    internal inner class RetrofitCall<T>(val request: HiRequest) : HiCall<T> {

        override fun execute(): HiResponse<T> {
            val realCall = createRealCall(request)
            val response = realCall.execute()
            return parseResponse(response)
        }


        private fun parseResponse(response: Response<ResponseBody>): HiResponse<T> {
            var rawData: String? = null
            if (response.isSuccessful) {
                val body = response.body()
                if (body != null) {
                    rawData = body.string()
                }
            } else {
                val body = response.errorBody()
                if (body != null) {
                    rawData = body.string()
                }
            }
            return hiConvert.convert<T>(rawData ?: "", object : TypeToken<T>() {}.type)
        }


        override fun enqueue(callback: HiCallback<T>) {
            val realCall = createRealCall(request)
            realCall.enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    val response = parseResponse(response)
                    callback?.onSuccess(response)
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    callback?.onFailed(throwable = t)
                }

            })
        }


        private fun createRealCall(request: HiRequest): Call<ResponseBody> {
            when (request.httpMethod) {
                HiRequest.METHOD.GET -> {
                    return apiService.get(
                        request.headers,
                        request.endPointUrl(),
                        request.parameters
                    )
                }
                HiRequest.METHOD.POST -> {
                    val requestBody: RequestBody? = buildRequestBody(request)
                    return apiService.post(request.headers, request.endPointUrl(), requestBody)
                }
                HiRequest.METHOD.PUT -> {
                    val requestBody: RequestBody? = buildRequestBody(request)
                    return apiService.put(request.headers, request.endPointUrl(), requestBody)
                }
                HiRequest.METHOD.DELETE -> {
                    return apiService.delete(request.headers, request.endPointUrl())
                }
                else -> {
                    throw IllegalStateException("hirestfull only support GET,POST for now,url=" + request.endPointUrl())
                }
            }
        }

    }

    private fun buildRequestBody(request: HiRequest): RequestBody? {
        val requestParams = request.parameters!!
        val builder = FormBody.Builder()
        var requestBody: RequestBody? = null
        val jsonObject = JSONObject()
        for ((key, value) in requestParams) {
            if (request.formPost) {
                builder.add(key, value)
            } else {
                jsonObject.put(key, value)
            }
        }
        requestBody = if (request.formPost) {
            builder.build()
        } else {
            RequestBody.create(
                MediaType.parse("application/json;utf-8"),
                jsonObject.toString()
            )
        }
        return requestBody
    }

    interface ApiService {
        @GET
        fun get(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String?,
            @QueryMap(encoded = true) params: MutableMap<String, String>?
        ): Call<ResponseBody>

        @POST
        fun post(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String?,
            @Body requestBody: RequestBody?
        ): Call<ResponseBody>

        @PUT
        fun put(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String,
            @Body requestBody: RequestBody?
        ): Call<ResponseBody>

        @DELETE //不可以携带requestBody
        fun delete(
            @HeaderMap headers: MutableMap<String, String>?,
            @Url url: String
        ): Call<ResponseBody>

    }
}