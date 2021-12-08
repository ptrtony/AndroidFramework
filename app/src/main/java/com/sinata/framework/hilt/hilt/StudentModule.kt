//package com.sinata.framework.hilt.hilt
//
//import androidx.core.app.ActivityCompat
//import com.sinata.framework.hilt.Student
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.DefineComponent
//import dagger.hilt.InstallIn
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
//@date 2021/10/21
// */
//
////hilt的写法
////你会发现hilt不支持从外面传参，很遗憾
//@Module
//@InstallIn(ActivityCompat::class)
//class StudentModule {
//    //创建对象提供者（@Provider）
//    @Provides
//    fun studentModel(): Student {
//        return Student(80)
//    }
//}