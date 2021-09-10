package com.sinata.hi_library.restful

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
        return Proxy.newProxyInstance(service.classLoader, arrayOf<Class<*>>(service)
        ) { proxy, method, args ->
            var methodParser = methodService[method]
            if (methodParser == null) {
                methodParser = MethodParser.parse(baseUrl, method)
                methodService.put(method, methodParser)
            }
            val request = methodParser.newRequest(method, args)
            scheduler.newCall(request)
        } as T
    }
}