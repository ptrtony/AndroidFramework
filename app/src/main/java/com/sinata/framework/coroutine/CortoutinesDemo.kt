package com.sinata.framework.coroutine

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
@author cjq
@Date 7/5/2021
@Time 12:33 PM
@Describe:
 */
class CortoutinesDemo {
    fun coroutines(){
        CoroutineScope(Dispatchers.Main).launch {

        }
    }
}