package com.sinata.framework.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 4/8/2021
 */

@Parcelize
data class HomeModel(
    val bannerList: List<HomeBanner>?,
    val subCategoryList: List<SubCategory>?,
    val goodsList: List<GoodsModel>?
) : Parcelable

@Parcelize
data class TabCategory(val categoryId: String, val categoryName: String, val goodsCount: String) :
    Parcelable


@Parcelize
data class HomeBanner(
    val cover: String,
    val createTime: String,
    val id: String,
    val sticky: Int,
    val subtitle: String,
    val title: String,
    val type: String,
    val url: String
) : Parcelable


@Parcelize
data class SubCategory(
    val categoryId: String,
    val groupName: String,
    val showType: String,
    val subCategoryIcon: String,
    val subCategoryId: String,
    val subCategoryName: String
) : Parcelable


@Parcelize
data class GoodsModel(
    val goodsId: String,
    val categoryId: String,
    val sliderImages: List<SliderImages>?,
    val marketPrice: String,
    val groupPrice: String,
    val completedNumText: String,
    val goodsName: String,
    val tags: String,
    val joinedAvatars: List<SliderImages>?,
    val createTime: String,
    val sliderImage: String
) : Parcelable


@Parcelize
data class SliderImages(val url: String, val type: Int) : Parcelable


fun selectPrice(groupPrice: String?, marketPrice: String?): String? {
    var price = if (marketPrice.isNullOrBlank()) groupPrice else marketPrice
    if (price?.startsWith("¥") != true) {
        price = "¥$price"
    }
    return price
}
