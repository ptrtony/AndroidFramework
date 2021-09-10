package com.sinata.framework.knowlege.jetpack.lifecycle

import android.os.Bundle
import androidx.activity.ComponentActivity

/**

Title:
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 2021/8/25
 */
class LifecycleDemoActivity : ComponentActivity(){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(LocationLifecycle())
        lifecycle.addObserver(LocationEventLifecycle())
    }
}