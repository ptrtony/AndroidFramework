package com.sinata.hi_library.cache

import androidx.room.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/5
 */

@Dao
interface CacheDao {

    @Insert(entity = Cache::class, onConflict = OnConflictStrategy.REPLACE)
    fun saveCache(cache: Cache): Long

    @Query("select * from cache where `key` =:key")
    fun getCache(key: String): Cache?

    @Delete(entity = Cache::class)
    fun deleteCache(cache: Cache)
}