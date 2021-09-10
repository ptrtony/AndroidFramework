package com.sinata.hi_library.utils

import android.app.Application
import java.lang.Exception

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


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