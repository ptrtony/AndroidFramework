package com.sinata.framework.design.adapter

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/22
 */
class UserInfo : IUserInfo {
    override fun getUserName(): String {
        println("姓名叫做...")
        return ""
    }

    override fun getHomeAddress(): String {
        println("这里是员工的家庭地址...")
        return ""
    }

    override fun getOfficeTelNumber(): String {
        println("办公电话是12345678910...");
        return ""
    }

    override fun getJobPosition(): String {
        println("这个人的职位是Boss...")
        return ""
    }

    override fun getHomeTelNumber(): String {
        println("员工的家庭电话是...")
        return ""
    }

    override fun getMobilePhoneNumber(): String {
        println("这个人的手机号是12345678910...")
        return ""
    }
}