package com.sinata.framework.toast

import android.content.Context
import android.widget.Toast
import java.util.concurrent.atomic.AtomicInteger

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/12/6
 */
class ToastDemo {

    fun toast(context: Context){
        Toast.makeText(context,"fasjdfhj",Toast.LENGTH_SHORT).show()
    }

    var atomicInteger:AtomicInteger = AtomicInteger()
    fun getAtomicInteger(){
        atomicInteger.getAndAdd(1)
        
    }


}