package com.sinata.framework.jetpack.livedata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/8/25
 */
class LiveDataViewModel : ViewModel() {

    var liveData :MutableLiveData<String> = MutableLiveData()

    fun getData() : MutableLiveData<String>?{
        return Transformations.map(liveData) { input ->
            val liveData = MutableLiveData<String>()
            liveData.postValue("${input} : hello word")
            return@map liveData
        }.value
    }

    fun postData(msg:String){
        liveData.postValue(msg)
    }

}