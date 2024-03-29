package com.sinata.framework.jetpack.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/9/5
 */

@Database(entities = [Cache::class, User::class], version = 2, exportSchema = true)
abstract class CacheDatabase : RoomDatabase() {
    abstract val cacheDao: CacheDao
    companion object{
        private var database:CacheDatabase? = null

        @Synchronized
        fun get(context: Context):CacheDatabase{
            if (database == null){
                //内存数据库，也就是这种数据库当中存储的数据，只能存储在内存中，进程被杀死后，数据被丢失
//                database = Room.inMemoryDatabaseBuilder(context,CacheDatabase::class.java).build()
                database = Room.databaseBuilder(context,CacheDatabase::class.java,"jetpack")
                        //允许在主线程操作数据库,默认是false，如果在主线程操作数据库会报错
                    .allowMainThreadQueries()
                    .addCallback(callback)
                    //指定数据查询数据时候的线程池
                    .setQueryExecutor {

                    }
                    //创建supportsplitopenhelper,frameworksplitopenhelperfactory
                        //可以利用他自行创建supportsplitopenhelper,来实现数据库的加密
//                    .openHelperFactory()
                        //做数据库的升级 1--2
                    .addMigrations(migration1_2)
                    .build()


            }
            return database!!
        }


        val callback = object:RoomDatabase.Callback(){
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
            }

            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
            }
        }

        val migration1_2 = object:Migration(1,2){
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("alter table table_cache add column cache_time LONG")
            }

        }
    }
}