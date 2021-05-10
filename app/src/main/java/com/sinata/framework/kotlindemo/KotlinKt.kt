package com.sinata.framework.kotlindemo

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

/**
@author cjq
@Date 25/4/2021
@Time 10:08 PM
@Describe:
 */

fun main() {
    var list = arrayListOf(1, 2, 3, 4, 5, 6)
    val list1 = arrayListOf(3, 4, 5, 6, 1, 2)
//    val result = list.sum{
//       return@sum it.toFloat()
//    }
//    print(result)

//    print(list.sum()(2))
//    testClosure(1)(2){
//        print(it)
//    }
    test1()

    println(list == list1)
}


//fun List<Int>.sum(callback:(scale:Int) -> Float):Int{
//    var result = 0
//    for (v in this){
//        result += v
//        callback.invoke(v)
//    }
//    return result
//}

fun List<Int>.sum(): (scale: Int) -> Float {
    return fun(scale: Int): Float {
        var result = 0
        for (v in this) {
            result += v * scale
        }
        return result.toFloat()
    }
}

fun testClosure(v1: Int): (v2: Int, (Int) -> Unit) -> Unit {
    return fun(v2: Int, printer: (Int) -> Unit) {
        return printer(v1 + v2)
    }

}

data class Result(val message: String, val code: Int)

fun test1() {
    val result = Result("success", 0)
    val (message, code) = result
    println("message:${message},code:${code}")
}

fun <T : View> Activity.find(@IdRes id: Int): T {
    return findViewById(id)
}

fun Int.onClick(activity: Activity, onClick: () -> Unit) {
    activity.find<View>(this).apply {
        setOnClickListener {
            onClick()
        }
    }
}