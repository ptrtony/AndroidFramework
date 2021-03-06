package com.sinata.hi_library.utils

import androidx.lifecycle.*
import java.util.concurrent.ConcurrentHashMap
import java.util.concurrent.ConcurrentMap

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/1
 */
object HiDataBus {

    private val eventMap = ConcurrentHashMap<String,StickyLiveData<*>>()
    fun <T> with(eventName: String): StickyLiveData<T> {
        //基于事件名称 订阅 分发消息
        //由于一个livedata 只能发送一种数据类型
        //所以 不同的event事件，需要使用不同的livedata实例 去分发数据
        var liveData = eventMap[eventName]
        if (liveData == null){
            liveData = StickyLiveData<T>(eventName)
        }
        return liveData as StickyLiveData<T>
    }

    class StickyLiveData<T>(private val eventName: String) : LiveData<T>() {
        var mStickyData : T ?= null
        var mVersion = 0




        fun setStickyData(stickyData:T){
            mStickyData = stickyData
            setValue(mStickyData)
            //只能在主线程去发送数据
        }


        fun postStickyData(stickyData: T){
            mStickyData = stickyData
            postValue(mStickyData)
            //不受线程的限制
        }


        override fun setValue(value: T?) {
            mVersion ++
            super.setValue(value)
        }

        override fun postValue(value: T?) {
            mVersion++
            super.postValue(value)
        }


        override fun observe(owner: LifecycleOwner, observer: Observer<in T>) {
            observerSticky(owner,false,observer)
        }

        fun observerSticky(owner: LifecycleOwner,sticky:Boolean,observer: Observer<in T>){
            //允许指定注册的观察者 是否需要关心粘性事件
            //sticky = true 如果之前存在已经发送的数据，那么这个observer会受到之前的粘性事件消息
            owner.lifecycle.addObserver(LifecycleEventObserver{ source, event ->
                //监听 宿主 发生销毁事件，主动把livedata移除掉
                if (event == Lifecycle.State.DESTROYED){
                    eventMap.remove(eventName)
                }

            })

            super.observe(owner,StickyObserver(this,sticky,observer))
        }



    }

    class StickyObserver<T>(val stickyLiveData:StickyLiveData<T>,
                            val sticky:Boolean,
                            val observer: Observer<in T>
    ):Observer<T>{
        //lastVersion 和 liveData 的version对齐的原因，就是为控制粘性事件的分发
        //sticky不等于true 只能接受到发送后的消息,如果要接受粘性事件，则sticky需要传递true
        private var lastVersion =stickyLiveData.mVersion
        override fun onChanged(t: T) {
            if (lastVersion >= stickyLiveData.mVersion){
                //说明stickyLiveData没有更新的数据需要发送
                if (sticky && stickyLiveData.mStickyData!=null){
                    observer.onChanged(stickyLiveData.mStickyData)
                }
                return
            }
            lastVersion = stickyLiveData.mVersion
            observer.onChanged(t)
        }
    }

}