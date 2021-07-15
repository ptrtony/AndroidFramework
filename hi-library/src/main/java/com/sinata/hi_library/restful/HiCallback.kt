package com.sinata.hi_library.restful

/**
 * callback回调
 */
interface HiCallback<T> {
    fun onSuccess(data:HiResponse<T>)
    fun onFailed(throwable: Throwable)
}