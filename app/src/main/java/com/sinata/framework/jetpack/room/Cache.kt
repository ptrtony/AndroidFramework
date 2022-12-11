package com.sinata.framework.jetpack.room

import android.graphics.Bitmap
import androidx.annotation.NonNull
import androidx.room.*
import javax.annotation.Nonnull

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/5
 */


@Entity(tableName = "table_cache")
class Cache {


    @PrimaryKey(autoGenerate = false)
    @Nonnull
    var cache_key:String = ""

    @ColumnInfo(name = "cacheId",defaultValue = "1")
    var cache_id : Long = 0

    @Ignore
    var bitmap:Bitmap ?= null


    //通过这个注解可以实现实体类中嵌套实体类并被嵌套的实体类需要标注Entity注解
    @Embedded
    var user:User?=null
}

@Entity
class User{
    @PrimaryKey(autoGenerate = false)
    @NonNull
    var name:String?=null
    var age = 10
}