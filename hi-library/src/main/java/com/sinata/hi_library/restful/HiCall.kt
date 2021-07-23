package com.sinata.hi_library.restful


interface HiCall<T> {
    fun execute():HiResponse<T>
    fun enqueue(callback: HiCallback<T>)


    interface Factory{
        fun newCall(request:HiRequest):HiCall<*>
    }
}