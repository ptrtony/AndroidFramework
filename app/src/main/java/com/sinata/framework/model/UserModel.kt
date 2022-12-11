package com.sinata.framework.model

import com.sinata.hi_ui.banner.core.HiBannerMo

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/8
 */

data class Notice(
    val id: String,
    val sticky: Int,
    val type:String,
    val title:String,
    val subtitle:String,
    val url:String,
    val cover:String,
    val createTime:String
): HiBannerMo()

data class CourseNotice(val total:Int,val list:List<Notice>)