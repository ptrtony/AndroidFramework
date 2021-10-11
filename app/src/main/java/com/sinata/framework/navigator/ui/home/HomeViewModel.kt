package com.sinata.framework.navigator.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.HomeApi
import com.sinata.framework.model.TabCategory
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse

class HomeViewModel(private val savedState: SavedStateHandle) : ViewModel() {

    fun queryCategoryTabs():LiveData<List<TabCategory>?>{
        val liveData = MutableLiveData<List<TabCategory>?>()
        val memCache = savedState.get<List<TabCategory>?>("categoryTabs")
        if (memCache != null){
            liveData.postValue(memCache)
            return liveData
        }

        ApiFactory.create(HomeApi::class.java)
            .queryTabList()
            .enqueue(object: HiCallback<List<TabCategory>>{
                override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                    if (response.successful()){
                        liveData.postValue(response.data)
                        savedState.set("categoryTabs",response.data)
                    }else{
//                        sendError(response)
                    }
                }

                override fun onFailed(throwable: Throwable) {

                }

            })

        return liveData
    }
}