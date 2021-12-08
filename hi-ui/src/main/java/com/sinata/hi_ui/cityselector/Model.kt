package com.sinata.hi_ui.cityselector

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/26
 */


const val TYPE_COUNTRY = "1"
const val TYPE_PROVINCE = "2"
const val TYPE_CITY = "3"
const val TYPE_DISTRICT = "4"

@Parcelize
open class Province : District(), Parcelable, Serializable {
    val citys = ArrayList<City>()

    //选择的市
    var selectCity: City? = null

    //选择的区
    var selectDistrict: District? = null
}

@Parcelize
open class City : District(), Parcelable, Serializable {
    val districts = ArrayList<District>()
}

@Parcelize
open class District : Parcelable, Serializable {
    var districtName: String? = null
    var id: String? = null
    var pid: String? = null
    var type: String? = null

    companion object {
        fun copyDistrict(src: District, dest: District) {
            dest.type = src.type
            dest.districtName = src.districtName
            dest.id = src.id
            dest.pid = src.pid
        }
    }
}