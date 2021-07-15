//package com.sinata.framework.arouter
//
//import android.content.Context
//import com.alibaba.android.arouter.facade.Postcard
//import com.alibaba.android.arouter.facade.annotation.Interceptor
//import com.alibaba.android.arouter.facade.annotation.Route
//import com.alibaba.android.arouter.facade.service.DegradeService
//import com.alibaba.android.arouter.launcher.ARouter
//
///**
//
//Title:
//Description:
//Copyright:Copyright(c)2021
//Company:成都博智维讯信息技术股份有限公司
//
//
//@author jingqiang.cheng
//@date 4/6/2021
// */
//
//@Route(path = "/degrade/detail/service")
//class DegradeServiceImpl : DegradeService {
//
//    override fun init(context: Context?) {
//
//    }
//
//    override fun onLost(context: Context?, postcard: Postcard?) {
//        ARouter.getInstance().build("/profile/unknown").greenChannel().navigation()
//    }
//}