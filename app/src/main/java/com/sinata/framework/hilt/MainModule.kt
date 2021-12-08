package com.sinata.framework.hilt

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Qualifier

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/20
 */
//
//@Module
//@InstallIn(FragmentComponent::class)
//object MainModule {
//    //自定义限定符@Qualifer,提供统一的接口，不同的实现
//    @Qualifier
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class useLoginServiceImpl
//
//    @Qualifier
//    @Retention(AnnotationRetention.RUNTIME)
//    annotation class useLoginServiceImpl2
//
//    @useLoginServiceImpl
//    @Provides
//    fun provideLoginServiceImpl():ILoginService{
//        return LoginServiceImpl()
//    }
//
//    @useLoginServiceImpl2
//    @Provides
//    fun provideLoginServiceImpl2():ILoginService{
//        return LoginServiceImpl()
//    }
//}