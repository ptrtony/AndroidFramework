package com.sinata.hi_library.restful

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 12/7/2021
 */

/**
 * 代理CallFactory创建出来的call对象，从而实现拦截器的派发工作
 */
class Scheduler(val callFactory: HiCall.Factory, val interceptors: MutableList<HiInterceptor>) {
    fun newCall(request: HiRequest): HiCall<*> {
        val newCall:HiCall<*> = callFactory.newCall(request)
        return ProxyCall(newCall,request)
    }


    internal inner class ProxyCall<T>(val delegate: HiCall<T>, val request: HiRequest) : HiCall<T>{
        override fun execute(): HiResponse<T> {
            dispatchInterceptor(request,null)
            val response = delegate.execute()
            dispatchInterceptor(request, response)
            return response
        }

        private fun dispatchInterceptor(request: HiRequest, response: HiResponse<T>?) {
            InterceptorChain(request, response).dispatch()
        }

        override fun enqueue(callback: HiCallback<T>?) {
            dispatchInterceptor(request,null)
            delegate.enqueue(object:HiCallback<T>{
                override fun onSuccess(data: HiResponse<T>) {
                    dispatchInterceptor(request,data)
                    callback?.onSuccess(data)
                }
                override fun onFailed(throwable: Throwable) {
                    callback?.onFailed(throwable)
                }

            })

        }


        internal inner class InterceptorChain(val request:HiRequest,val response:HiResponse<T>?):HiInterceptor.Chain{

            //分发的是第几个拦截器
            var callIndex = 0
            override val isRequestPeriod: Boolean
                get() = response == null

            override fun request(): HiRequest {
                return request
            }

            override fun response(): HiResponse<*>? {
                return response
            }

            fun dispatch(){
                val interceptor = interceptors[callIndex]
                val intercept = interceptor.interceptor(this)
                callIndex ++
                if (!intercept && callIndex < interceptors.size){
                    dispatch()
                }
            }

        }

    }
}