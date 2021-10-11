package com.sinata.framework.coroutine

import kotlinx.coroutines.*

/**
@author cjq
@Date 7/5/2021
@Time 12:33 PM
@Describe:
 */


fun main() = runBlocking {
    launch {
        doWorld()
    }
    println("hello")
}


suspend fun doWorld() = coroutineScope {
    val job = launch {
        delay(2000L)
        println("World 2")
    }

    launch {
        delay(1000L)
        println("World 1")
    }

    job.join()
    println("World")
}