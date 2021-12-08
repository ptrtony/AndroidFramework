//package com.sinata.framework.hilt
//
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import com.sinata.common.ui.view.EmptyView
//import com.sinata.framework.R
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
///**
//Title:
//Description:
//Copyright:Copyright(c)2021
//Company:成都博智维讯信息技术股份有限公司
//@author jingqiang.cheng
//@date 2021/10/20
// */
//
//
////声明该类是一个可以注入的入口类。仅支持在一下类型中进行依赖注入
////this support appcompatActivity,androidx.fragment,view,services,and broadcastReceiver
////不支持contentProvider，不支持你自定义且不属于以上类型的类
//
//@AndroidEntryPoint
//class HomeActivity : AppCompatActivity(){
//
//    //声明该对象需要被动的动态注入
//    @Inject
//    lateinit var emptyView:EmptyView
//
//    @Inject
//    @JvmField
//    var iLoginService: ILoginService?=null
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//        //编译时生成Hilt-HomeActivity，在supper面前实例化该类，进行成员变量赋值
//
//    }
//}