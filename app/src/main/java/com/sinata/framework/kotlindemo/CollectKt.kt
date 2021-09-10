package com.sinata.framework.kotlindemo

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 28/7/2021
 */

fun main(){
    mapDemo()
}


fun mapDemo(){
    val colors = setOf("red","brown","grey")
    val animals = listOf("fox","bear","wolf")
    println(colors zip animals)
    val twoAnimals = listOf("fox","bear")
    println(colors.zip(twoAnimals))
}