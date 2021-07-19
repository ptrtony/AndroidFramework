package com.sinata.common.utils

import android.annotation.SuppressLint
import android.app.Application
import java.lang.Exception

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 18/7/2021
 */
object AppGlobal {
    var application:Application?=null
    fun get() : Application?{
        if (application == null){
          try {
              application = Class.forName("android.app.ActivityThread")
                  .getMethod("currentApplication")
                  .invoke(null) as Application
          }catch (e:Exception){
              e.printStackTrace()
          }
        }
        return application
    }
}