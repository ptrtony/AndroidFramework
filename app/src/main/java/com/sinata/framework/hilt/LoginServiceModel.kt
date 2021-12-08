package com.sinata.framework.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/20
 */

//@InstallIn(FragmentComponent::class)//不可以在activity下使用
//@Module
//abstract class LoginServiceModel {
//    //1. 函数返回类型告诉Hilt提供了哪个接口的实例
//    //2. 函数参数告诉Hilt提供哪个实现
//    //3. 没有指定作用域scope，则每次都会生成新的实例对象
//    @Binds
//    abstract fun bindLoginService(iml: LoginServiceImpl): ILoginService
//}