package com.sinata.framework.fragment.detail

import android.text.TextUtils
import androidx.lifecycle.*
import com.sinata.framework.BuildConfig
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.framework.http.api.DetailApi
import com.sinata.framework.http.api.FavoriteApi
import com.sinata.framework.model.DetailModel
import com.sinata.framework.model.Favorite
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import java.lang.Exception

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/14
 */
class DetailViewModel(val goodsId:String?) : ViewModel() {

    companion object {
        private class DetailViewModelFactory(val goodsId: String?) :
            ViewModelProvider.NewInstanceFactory() {

            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                try {
                    val constructor = modelClass.getDeclaredConstructor(String::class.java)
                    return constructor.newInstance(goodsId)
                } catch (e: Exception) {

                }
                return super.create(modelClass)
            }
        }


        fun get(goodsId: String?, owner: ViewModelStoreOwner): DetailViewModel {
            return ViewModelProvider(
                owner,
                DetailViewModelFactory(goodsId)
            ).get(DetailViewModel::class.java)
        }

    }

    fun queryDetailPage() :  LiveData<DetailModel>{
        val dataPage = MutableLiveData<DetailModel>()
        if(!TextUtils.isEmpty(goodsId)){
           ApiFactory.create(DetailApi::class.java)
               .queryDetail(goodsId!!)
               .enqueue(object:HiCallback<DetailModel>{
                   override fun onSuccess(response: HiResponse<DetailModel>) {
                       if (response.successful() && response.data != null){
                           dataPage.postValue(response.data)
                       }else{
                           dataPage.postValue(null)
                       }
                   }

                   override fun onFailed(throwable: Throwable) {
                       dataPage.postValue(null)
                       if (BuildConfig.DEBUG){
                           throwable.printStackTrace()
                       }
                   }
               })
        }
        return dataPage
    }


    fun toggleFavorite():LiveData<Boolean>{
        val toggleLiveData = MutableLiveData<Boolean>()
        if (!TextUtils.isEmpty(goodsId)){
            ApiFactory.create(FavoriteApi::class.java)
                .favorite(goodsId!!)
                .enqueue(object:HiCallback<Favorite>{
                    override fun onSuccess(response: HiResponse<Favorite>) {
                        if (response.successful() && response.data!=null){
                            toggleLiveData.value = response.data!!.isFavorite
                        }
                    }

                    override fun onFailed(throwable: Throwable) {

                    }

                })
        }


        return toggleLiveData
    }
}