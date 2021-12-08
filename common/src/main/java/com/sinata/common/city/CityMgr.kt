package com.sinata.common.city

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sinata.hi_library.cache.HiStorage
import com.sinata.hi_library.executor.HiExecutor
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_library.restful.HiCallback
import com.sinata.hi_library.restful.HiResponse
import com.sinata.hi_library.restful.http.ApiFactory
import com.sinata.hi_ui.cityselector.*
import java.util.concurrent.atomic.AtomicBoolean

/**

Title:
Description: 获取城市数据，优先本地缓存，否则接口请求再更新本地缓存，同时一刻多次调用只有一次生效
 *            内存常驻，所以可以考虑手动清除LiveData.value
Copyright:Copyright(c)2021
@date 2021/10/26
 */

object CityMgr {
    private const val KEY_CITY_DATA_SET = "city_data_set"
    private val liveData = MutableLiveData<List<Province>?>()

    //是否正在加载数据... 同一时刻只有一个线程，或者一处能够发起数据的加载
    private val isFetching = AtomicBoolean(false)
    fun getCityData(): LiveData<List<Province>?> {
        //发送过一次数据之后，这个数据List<Province>会存储value
        if (!isFetching.compareAndSet(false, true) && liveData.value == null) {
            getCache { cache ->
                if (cache != null) {
                    liveData.postValue(cache)
                    isFetching.compareAndSet(true, false)
                } else {
                    fetchRemote {
                        liveData.postValue(it)
                        isFetching.compareAndSet(true, false)
                    }
                }
            }
        }
        return liveData
    }

    private fun fetchRemote(callback: (List<Province>?) -> Unit) {
        ApiFactory.create(CityApi::class.java)
            .listCitys()
            .enqueue(object : HiCallback<CityModel> {

                override fun onSuccess(response: HiResponse<CityModel>) {
                    if (response.data?.list?.isNullOrEmpty() == false) {
                        groupByProvince(response.data!!.list) { groupList ->
                            saveGroupProvince(groupList)
                            callback(groupList)
                        }
                    } else {
                        HiLog.e("response data list is empty or null")
                        callback(null)
                    }
                }

                override fun onFailed(throwable: Throwable) {
                    if (!TextUtils.isEmpty(throwable.message)) {
                        HiLog.e(throwable.message)
                    }

                    callback(null)
                }
            })
    }

    //持久化数据
    private fun saveGroupProvince(groupList: List<Province>?) {
        if (groupList.isNullOrEmpty()) return
        HiExecutor.executor(runnable = Runnable {
            HiStorage.saveCache(KEY_CITY_DATA_SET, groupList)
        })
    }

    //对数据进行分组，生成三级数据结构
    private fun groupByProvince(list: List<District>, callback: (List<Province>?) -> Unit) {
        //是为了收集所有的省，同时也是为了TYPE_CITY,快速找到自己所在的Province对象
        val provinceMaps = HashMap<String, Province>()
        //是为了TYPE_DISTRICT快速找到自己所在的City对象
        val cityMaps = HashMap<String, City>()
        HiExecutor.executor(runnable = Runnable {
            for (element in list) {
                if (TextUtils.isEmpty(element.id)) continue
                when (element.type) {

                    TYPE_COUNTRY -> {

                    }
                    TYPE_PROVINCE -> {
                        val province = Province()
                        District.copyDistrict(element, province)
                        provinceMaps[element.id!!] = province
                    }
                    TYPE_CITY -> {
                        val city = City()
                        District.copyDistrict(element, city)
                        val province = provinceMaps[element.pid]
                        province?.citys?.add(city)
                        cityMaps[city.id!!] = city
                    }
                    TYPE_DISTRICT -> {
                        val city = cityMaps[element.pid]
                        city?.districts?.add(element)
                    }
                }
            }

            callback(ArrayList(provinceMaps.values))
        })


    }

    private fun getCache(callback: (List<Province>?) -> Unit) {
        val cache = HiStorage.getCache<List<Province>?>(KEY_CITY_DATA_SET)
        HiExecutor.executor(runnable = Runnable {
            callback(cache)
        })
    }
}