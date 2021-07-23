package com.sinata.hi_library.restful

import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import java.util.concurrent.ConcurrentHashMap


open class HiRestful constructor(val baseUrl: String, val callFactory: HiCall.Factory) {
    private var interceptors: MutableList<HiInterceptor> = mutableListOf()
    private var methodService: ConcurrentHashMap<Method,MethodParser> = ConcurrentHashMap()
    fun addInterceptor(interceptor: HiInterceptor) {
        interceptors.add(interceptor)
    }
    private var scheduler:Scheduler
    init {
        scheduler = Scheduler(callFactory,interceptors)
    }

    fun <T> create(service: Class<T>): T {
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service),object:InvocationHandler{
            override fun invoke(proxy: Any?, method: Method, args: Array<out Any>?): Any {
                var methodParser = methodService[method]
                if (methodParser == null){
                    methodParser = MethodParser.parse(baseUrl,method)
                    methodService.put(method,methodParser)
                }
                val request = methodParser.newRequest(method, args)
                return scheduler.newCall(request)
            }
        }) as T
    }
}