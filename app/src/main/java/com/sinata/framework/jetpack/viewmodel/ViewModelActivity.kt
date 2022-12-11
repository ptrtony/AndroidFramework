package com.sinata.framework.jetpack.viewmodel

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/4
 */
class ViewModelActivity : ComponentActivity() {
    private lateinit var viewModel:ViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val factory = ViewModelProvider.NewInstanceFactory()
        val mViewModel = factory.create(AndroidViewModel::class.java)
        viewModel = ViewModelProvider(this,factory).get(AndroidViewModel::class.java)
    }
}