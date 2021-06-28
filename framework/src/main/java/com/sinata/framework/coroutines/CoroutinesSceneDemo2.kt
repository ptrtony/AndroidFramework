package com.sinata.framework.coroutines

import kotlinx.coroutines.suspendCancellableCoroutine

/**

Title:
Description:
Copyright:Copyright(c)2021


@author jingqiang.cheng
@date 27/6/2021
 */
class CoroutinesSceneDemo2 {


    suspend fun parseAssetFile(fileName:String):String{
        return suspendCancellableCoroutine { continuation ->
            Thread {

                continuation.resumeWith(Result.success("assets file content"))
            }.start()
        }
    }
}