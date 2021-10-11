package com.sinata.framework.fragment.home

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.sinata.framework.http.ApiFactory
import com.sinata.framework.http.api.HomeApi
import com.sinata.framework.model.TabCategory
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/10
 */
class HomeViewModel private constructor(val savedState: SavedStateHandle) : ViewModel() {


    fun queryTabList() : LiveData<List<TabCategory>>{
        val liveData = MutableLiveData<List<TabCategory>>()
        val memCache = savedState.get<List<TabCategory>>("categoryTabs")
        if (memCache!=null){
            liveData.postValue(memCache)
            return liveData
        }

        ApiFactory.create(HomeApi::class.java).queryTabList().enqueue(object : HiCallback<List<TabCategory>> {
            override fun onSuccess(response: HiResponse<List<TabCategory>>) {
                val data = response.data
                if (response.successful() && data != null) {
                    liveData.postValue(data)
                    savedState.set("categoryTabs",data)
                }
            }

            override fun onFailed(throwable: Throwable) {}
        })

        return liveData
    }

}