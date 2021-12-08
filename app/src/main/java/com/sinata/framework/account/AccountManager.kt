package com.sinata.framework.account

import android.app.Application
import android.content.Context
import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.sinata.framework.arouter.LoginActivity
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.framework.http.api.AccountApi
import com.sinata.framework.model.UserProfile
import com.sinata.hi_library.cache.HiStorage
import com.sinata.hi_library.executor.HiExecutor
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_library.utils.AppGlobal
import com.sinata.hi_library.utils.SPUtils

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/6
 */
object AccountManager {
    private var userProfile: UserProfile? = null
    private var boardPass: String? = null
    private val KEY_BOARDING_PASS = "KEY_BOARDING_PASS"
    private val KEY_USER_PROFILE = "KEY_USER_PROFILE"
    private val loginLiveData = MutableLiveData<Boolean>()
    private val loginForeverObserver = mutableListOf<Observer<Boolean>>()

    private val profileLiveData = MutableLiveData<UserProfile>()
    private val profileForeverObservers = mutableListOf<Observer<UserProfile?>>()

    @Volatile
    private var isFetching = false
    fun login(context: Context? = AppGlobal.get(), observer: Observer<Boolean>) {
        if (context is LifecycleOwner) {
            loginLiveData.observe(context, observer)
        } else {
            loginLiveData.observeForever(observer)
            loginForeverObserver.add(observer)
        }

        val intent = Intent(context, LoginActivity::class.java)
        if (context is Application) {
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }

        if (context == null) {
            throw IllegalAccessException("context must not be null")
        }

        context.startActivity(intent)
    }

    fun loginSuccess(boardPass: String) {
        SPUtils.putString(KEY_BOARDING_PASS, boardPass)
        this.boardPass = boardPass
        loginLiveData.value = true
        clearLoginForeverObserver()
    }

    private fun clearLoginForeverObserver() {
        for (observer in loginForeverObserver){
            loginLiveData.removeObserver(observer)
        }
        loginForeverObserver.clear()
    }

    fun getBoardingPass():String?{
        if (TextUtils.isEmpty(boardPass)){
            boardPass = SPUtils.getString(KEY_BOARDING_PASS)
        }
        return boardPass
    }


    fun isLogin() : Boolean{
        return !TextUtils.isEmpty(getBoardingPass())
    }


    @Synchronized
    fun getUserProfile(lifecycleOwner: LifecycleOwner?,observer:Observer<UserProfile?>,onlyCache:Boolean){
        if (lifecycleOwner == null){
            profileLiveData.observeForever(observer)
            profileForeverObservers.add(observer)
        }else{
            profileLiveData.observe(lifecycleOwner,observer)
        }

        if (userProfile!=null && onlyCache){
            profileLiveData.postValue(userProfile)
            return
        }

        if (isFetching){
            return
        }

        isFetching = true
        ApiFactory.create(AccountApi::class.java)
            .profile()
            .enqueue(object:HiCallback<UserProfile>{
                override fun onSuccess(response: HiResponse<UserProfile>) {
                    userProfile = response.data
                    if (response.successful() && userProfile!=null){
                        HiExecutor.executor(runnable = Runnable {
                            HiStorage.saveCache(KEY_USER_PROFILE,userProfile)
                            isFetching = false
                        })
                        profileLiveData.value = userProfile

                    }else{
                        profileLiveData.value = null
                    }
                    clearProfileForeverObserver()
                }

                override fun onFailed(throwable: Throwable) {
                    isFetching = false
                    profileLiveData.value = null
                    clearProfileForeverObserver()
                }
            })
    }

    private fun clearProfileForeverObserver() {
        for (observer in profileForeverObservers){
            profileLiveData.removeObserver(observer)
        }
        profileForeverObservers.clear()
    }

}