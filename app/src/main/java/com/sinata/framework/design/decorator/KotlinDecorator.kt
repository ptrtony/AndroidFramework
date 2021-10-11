package com.sinata.framework.design.decorator

/**

Title:
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 2021/10/7
 */
fun Panda.bamboo(decorator: () -> Unit) {
    eat()
    println("可以吃竹子")
    decorator()
}

fun Panda.carrot(decorator: () -> Unit) {
    println("可以吃胡萝卜")
    decorator()
}

fun main(){
    Panda().run {
        bamboo {
            carrot {

            }
        }
    }
}