package com.sinata.framework.hilt

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.WorkSource
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ApplicationComponent

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/21
 */

//@EntryPoint
//@InstallIn(ApplicationComponent::class)
//interface InitializerEntryPoint {
//    fun injectWorkService():WorkService{
//        return object:WorkService{}
//    }
//
//    companion object{
//        fun resolve(context: Context):InitializerEntryPoint{
//            //如果该模块的生命周期是application，在创建实例时需要使用fromapplication
//            //对应的还有fromactivity，fromfragment，fromview
//            //具体使用那个方法需要和InstallIn指定的组件类型匹配
//            return EntryPointAccessors.fromApplication(context,InitializerEntryPoint::class.java)
//        }
//    }
//
//    abstract class WorkContentProvider:ContentProvider(){
//        override fun onCreate(): Boolean {
//            val service = InitializerEntryPoint.resolve(context!!).injectWorkService()
//            return true
//        }
//
//    }
//}