package com.sinata.framework.model

import com.sinata.hi_ui.banner.core.HiBannerMo

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 22/7/2021
 */
data class Notice(
    val id: String, val sticky: String, val type: Int, val title: String,
    val subtitle: String, val url: String, val cover: String, val createTime: String
) : HiBannerMo() {
    override fun getBannerUrl(): String {
        return url
    }
}