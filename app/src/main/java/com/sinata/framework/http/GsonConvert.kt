package com.sinata.framework.http

import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.JsonObject
import com.google.gson.reflect.TypeToken
import com.sinata.hi_library.restful.HiConvert
import com.sinata.hi_library.restful.HiResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**
@author jingqiang.cheng
@date 14/7/2021
 */
class GsonConvert : HiConvert{
    private var gson: Gson
    init {
        gson = Gson()
    }

    override fun <T> convert(rawData: String, dataType: Type): HiResponse<T> {
        val response : HiResponse<T> = HiResponse()
        try {
            val jsonObject = JSONObject(rawData)
            response.code = jsonObject.optInt("code")
            response.msg = jsonObject.optString("msg")
            val data = jsonObject.opt("data")
            if (data is JsonObject || data is JsonArray){
                if (response.code == HiResponse.SUCCESS){
                    response.data = gson.fromJson(data.toString(),dataType)
                }else{
                    response.errorData = gson.fromJson<MutableMap<String,String>>(data.toString(),object:TypeToken<MutableMap<String,String>>(){
                    }.type)
                }
            }else{
                response.data = data as T?
            }

        }catch (e:JSONException){
            e.printStackTrace()
            response.code = -1
            response.msg = e.message.toString().trim()
        }
        response.rawData = rawData
        return response
    }
}