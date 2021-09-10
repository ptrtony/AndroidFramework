package com.sinata.framework.flow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/7
 */


fun simple(): Flow<Int> = flow {

}


fun main() {
    runBlocking {
        asFlow()
    }
}

suspend fun asFlow(){
    (1..2).asFlow()
        .map { request-> request.toString() }
        .collect { value ->
        print(value)
    }
}