package com.sinata.hi_library.restful


interface HiInterceptor {
    fun interceptor(chain: Chain): Boolean

    /**
     * Chain 对象会在我们派发拦截器的时候 创建
     */
    interface Chain {

        val isRequestPeriod:Boolean get() = false

        fun request(): HiRequest

        /**
         * 这个response对象 在网络发起之前。为空
         */
        fun response(): HiResponse<*>?
    }
}