package com.sinata.hi_library.cache

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sinata.hi_library.utils.AppGlobal

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/5
 */

@Database(entities = [Cache::class], version = 1,exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {
    companion object {
        private var cacheDatabase: CacheDatabase
        fun get(): CacheDatabase {
            return cacheDatabase
        }

        init {
            val applicationContext = AppGlobal.get()!!.applicationContext
            cacheDatabase =
                Room.databaseBuilder(applicationContext, CacheDatabase::class.java, "cache").build()
        }
    }

    abstract val cacheDao: CacheDao
}