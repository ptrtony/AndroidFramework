package com.sinata.framework.model

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/14
 */


data class DetailModel(
    val categoryId: String,
    val commentCountTitle: String,
    val commentModels: List<CommentModel>?,
    val commentTags: String?,
    val completedNumText: String,
    val createTime: String,
    val flowGoods: List<GoodsModel>?,
    val gallery: List<SliderImages>?,
    val goodsAttr: List<MutableMap<String, String>>?,
    val goodsDescription: String?,
    val goodsId: String,
    val goodsName: String,
    val groupPrice: String,
    val hot: Boolean,
    val marketPrice: String,
    val shop: Shop?,
    val similarGoods: List<GoodsModel>?,
    val sliderImage: String,
    val sliderImages: List<SliderImages>?,
    val tags: String,
    val isFavorite:Boolean
) {


    data class CommentModel(
        val avatar:String,
        val content:String,
        val nickname:String
    )


    data class Shop(
        val completedNum:String,
        val evaluation:String,
        val goodsNum:String,
        val logo:String,
        val name:String
    )

}