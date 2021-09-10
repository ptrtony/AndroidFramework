package com.sinata.framework.knowlege.jetpack.lifecycle

import androidx.lifecycle.*

/**

Title:
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 2021/8/25
 */
class LocationLifecycle : LifecycleObserver {

    @OnLifecycleEvent(Lifecycle.Event.ON_START)
    fun onStart(){

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_DESTROY)
    fun onDestroy(){

    }
}



class LocationEventLifecycle : LifecycleEventObserver{
    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        if (event == Lifecycle.Event.ON_START){

        }else if (event == Lifecycle.Event.ON_DESTROY){

        }
    }

}