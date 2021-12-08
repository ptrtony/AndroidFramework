package com.sinata.framework.design.adapter

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/22
 */
interface IUserInfo {

    //获取用户姓名
    fun getUserName(): String

    //获取家庭住址
    fun getHomeAddress(): String

    //获取手机号码
    fun getOfficeTelNumber(): String

    //获取这个人的职位
    fun getJobPosition(): String

    //获取家庭电话号码
    fun getHomeTelNumber(): String

    fun getMobilePhoneNumber(): String
}