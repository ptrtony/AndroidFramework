package com.sinata.framework.model.entity

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/25
 */
@Parcelize
data class Address(
    var province: String,
    var city: String,
    var area: String,
    var detail: String,
    var receiver: String,
    var phoneNum: String,
    var id: String,
    var uid: String
) : Parcelable