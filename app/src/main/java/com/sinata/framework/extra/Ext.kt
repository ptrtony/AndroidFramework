package com.sinata.framework.extra

import android.app.Activity
import android.widget.Toast

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2022/3/11
 */
fun Activity.extraBundle(){
    val context = applicationContext;
    Toast.makeText(context,"toast",Toast.LENGTH_LONG).show()
}

fun highFunction(callback:()->Unit){
    callback.invoke()
}

fun highFunction(callback:(Int) -> Boolean){
    callback.invoke(1)
}