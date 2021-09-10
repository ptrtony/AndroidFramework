package com.sinata.framework.knowlege.jetpack.livedata

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.Observer
import com.sinata.framework.R

/**

Title:
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 2021/8/25
 */
class LiveDataDemoActivity : ComponentActivity (){
    private var viewModel:LiveDataViewModel?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_live_data_demo)
        viewModel = defaultViewModelProviderFactory.create(LiveDataViewModel::class.java)


        viewModel?.getData()?.observe(this, Observer {
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })
    }

    fun onSubmitClick(view :View){
        viewModel?.postData("Tom")
    }
}