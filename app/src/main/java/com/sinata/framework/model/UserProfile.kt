package com.sinata.framework.model

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 22/7/2021
 */
data class UserProfile(val isLogin:Boolean,val favoriteCount:Int,val browseCount:Int,val learnMinutes:Int,
val userName:String?,val avatar:String,
val bannerNoticeList:List<Notice>)