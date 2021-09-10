package com.sinata.framework.model

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 22/7/2021
 */
data class UserProfile(val isLogin:Boolean,val favoriteCount:Int,val browseCount:Int,val learnMinutes:Int,
val userName:String?,val avatar:String,
val bannerNoticeList:List<Notice>)