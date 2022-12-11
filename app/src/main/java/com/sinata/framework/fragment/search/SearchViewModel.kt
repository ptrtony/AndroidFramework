package com.sinata.framework.fragment.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.sinata.framework.fragment.home.GoodsItem
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/16
 */
class SearchViewModel : ViewModel() {

    val goodSearchLiveData : LiveData<List<GoodsItem>> = object:MutableLiveData<List<GoodsItem>>(){

    }

    fun querySearch(keyword: String):LiveData<List<Keyword>>{
        val listData = MutableLiveData<List<Keyword>>()
        ApiFactory.create(SearchApi::class.java)
            .querySearchApi(keyword)
            .enqueue(object:HiCallback<List<Keyword>>{
                override fun onSuccess(response: HiResponse<List<Keyword>>) {
                    if (response.successful()){
                        listData.postValue(response.data)
                    }else{
                        listData.postValue(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    listData.postValue(null)
                }

            })

        return listData
    }

    fun saveHistory(keyword: Keyword) {

    }
}