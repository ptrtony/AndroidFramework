package com.sinata.framework.jetpack.viewmodel

import androidx.lifecycle.MutableLiveData

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/4
 */
class AndroidModel {
    val liveData = MutableLiveData<String>()
    fun getData(){
        liveData.postValue("hello world")
    }
}