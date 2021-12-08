//package com.sinata.framework.hilt.dagger
//
//import dagger.Module
//import dagger.Provides
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
////1.创建对象提供模型（@Module）
////除了提供带参的对象的提供者以外，还要有提供参数的提供者，二则缺一不可
//@Module
//class StudentModule private constructor(val age:Int){
//
//
//    //提供参数的提供者
//    @Provides
//    fun ageProvider():Int{
//        return age
//    }
//
//    //创建对象提供者(@Provider)
//    @Provides
//    fun studentProvider():Student{
//        return Student(age)
//    }
//
//}