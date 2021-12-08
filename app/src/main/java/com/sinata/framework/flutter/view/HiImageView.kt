package com.sinata.framework.flutter.view

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/12/6
 */
class HiImageView @JvmOverloads constructor(context:Context,attrs: AttributeSet?=null,defStyleAttr:Int = 0) : AppCompatImageView(context, attrs, defStyleAttr) {
    fun setUrl(url:String?){
        Glide.with(context).load(url).into(this)
    }

}