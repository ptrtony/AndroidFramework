package com.sinata.framework.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.AndroidViewModel

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/4
 */



class AndroidViewModel constructor(val savedState:SavedStateHandle) : ViewModel(){
    private val KEY_HOME_PAGE_DATA = "key_home_page_data"
    private val liveData = MutableLiveData<List<AndroidModel>>()

    fun loadAndroidData():LiveData<List<AndroidModel>>{
        //1.from memory
        if (liveData.value == null){
            val memoryData = savedState.get<List<AndroidModel>>(KEY_HOME_PAGE_DATA)
            liveData.postValue(memoryData)
            return liveData

            //2.from remote
            val remoteData = fetchDataFromRemote()
            savedState.set(KEY_HOME_PAGE_DATA,remoteData)
            liveData.postValue(remoteData)
        }

        return liveData
    }

    private fun fetchDataFromRemote(): List<AndroidModel> {
        return ArrayList<AndroidModel>()
    }
}

class AndroidViewModel1 constructor(application: Application,savedState: SavedStateHandle): AndroidViewModel(application){

}