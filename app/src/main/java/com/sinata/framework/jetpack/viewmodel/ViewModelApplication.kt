package com.sinata.framework.jetpack.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner

/**

Title:
Description: 跨页面之间的数据共享
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/4
 */
class ViewModelApplication : Application() ,ViewModelStoreOwner{

    private val appViewModelStore:ViewModelStore by lazy {
        ViewModelStore()
    }

    override fun getViewModelStore(): ViewModelStore {
        return appViewModelStore
    }

    val viewModel = ViewModelProvider(this).get(AndroidViewModel::class.java)

}