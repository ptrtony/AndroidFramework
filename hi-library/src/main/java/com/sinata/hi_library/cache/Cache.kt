package com.sinata.hi_library.cache

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/5
 */

@Entity(tableName = "cache")
class Cache {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    var key: String = ""

    var data: ByteArray? = null
}