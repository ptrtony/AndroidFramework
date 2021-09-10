package com.sinata.framework.jetpack.room

import androidx.lifecycle.LiveData
import androidx.room.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/5
 */


@Dao //全称database access object 数据访问对象 这里会执行增删改查的方法
interface CacheDao {

    @Query("select * from table_cache where 'cache_key'=:keyword limit 1")
    fun query(keyword: String): Cache

    //可以通过LiveData以观察者的形式获取数据库的数据，可以避免不必要的npe
    //更重要的是他可以监听数据库中表的变化 insert update delete room会自动读取表中的数据
    @Query("select * from table_cache")
    fun query2(): LiveData<List<Cache>> //rxjava observer

    @Delete(entity = Cache::class)
    fun delete(cache_key: String)


    @Insert(entity = Cache::class, onConflict = OnConflictStrategy.REPLACE)
    fun insert(cache: Cache)

    @Update(entity = Cache::class)
    fun update()


}