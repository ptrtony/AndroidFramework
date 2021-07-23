package com.sinata.common.ui.view

import android.content.Context
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import com.sinata.common.R

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 21/7/2021
 */
class EmptyView @JvmOverloads constructor(context: Context,attributeSet: AttributeSet?,defStyleAttr:Int = 0):LinearLayout(context,attributeSet,defStyleAttr) {
    private var icon:IconFontTextView
    private var desc:TextView
    private var title:TextView
    private var button:Button
    private var emptyAction:IconFontTextView
    init {
        orientation = VERTICAL
        gravity = Gravity.CENTER
        LayoutInflater.from(context).inflate(R.layout.layout_empty_view,this,true)
        icon = findViewById<IconFontTextView>(R.id.empty_icon)
        desc = findViewById(R.id.empty_text)
        title = findViewById(R.id.empty_title)
        button = findViewById(R.id.empty_action)
        emptyAction = findViewById(R.id.empty_tips)
        val typeface:Typeface = Typeface.createFromAsset(context.assets,"fonts/iconfont.ttf")
        icon.typeface = typeface

    }

    /**
     * 设置icon 需要在string.xml中定义，iconfont.ttf中的unicode码
     */
    fun setIcon(@StringRes iconRes:Int){
        icon.setText(iconRes)
    }

    fun setTitle(text:String){
        title.text = text
        title.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    fun setDesc(text:String){
        desc.text = text
        desc.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
    }

    @JvmOverloads
    fun setHelpAction(actionId:Int = R.string.if_detail,listener:OnClickListener){
        emptyAction.setText(actionId)
        emptyAction.setOnClickListener(listener)
        emptyAction.visibility = View.VISIBLE

        if (actionId == -1){
            emptyAction.visibility = View.GONE
        }

    }

    fun setButton(text:String,listener:OnClickListener){
        if (TextUtils.isEmpty(text)){
            button.visibility = View.GONE
        }else{
            button.visibility = View.VISIBLE
            button.text = text
            button.setOnClickListener(listener)
        }
    }
}