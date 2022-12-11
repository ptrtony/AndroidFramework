package com.sinata.framework.jetpack.livedata

import androidx.lifecycle.MutableLiveData

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/12/5
 */
public class LiveDataDemo{
    val liveData = MutableLiveData<String>()
    fun update(){
        liveData.postValue("你好")
    }
}