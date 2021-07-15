package com.sinata.framework.http

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.sinata.hi_library.restful.HiConvert
import com.sinata.hi_library.restful.HiResponse
import org.json.JSONException
import org.json.JSONObject
import java.lang.reflect.Type

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


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
            val data = jsonObject.optString("data")
            if (response.code == HiResponse.SUCCESS){
                response.data = gson.fromJson(data,dataType)
            }else{
                response.errorData = gson.fromJson<MutableMap<String,String>>(data,object:TypeToken<MutableMap<String,String>>(){
                }.type)
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