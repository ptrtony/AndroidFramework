//package com.sinata.framework.hilt.hilt
//
//import android.graphics.Color
//import android.os.Bundle
//import android.util.TypedValue
//import android.view.Gravity
//import android.widget.FrameLayout
//import android.widget.TextView
//import androidx.appcompat.app.AppCompatActivity
//import com.sinata.framework.hilt.Student
//import dagger.hilt.EntryPoint
//import dagger.hilt.android.AndroidEntryPoint
//import javax.inject.Inject
//
///**
//
//Title:
//Description:
//Copyright:Copyright(c)2021
//Company:成都博智维讯信息技术股份有限公司
//
//
//@author jingqiang.cheng
//@date 2021/10/21
// */
//
//@AndroidEntryPoint
//class HiltStudentActivity : AppCompatActivity() {
//
//    @JvmField
//    @Inject
//    var student:Student?=null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        student?.age = 17
//        val text = TextView(this)
//        text.text = "${student?.age}"
//        text.setTextSize(TypedValue.COMPLEX_UNIT_PX,14f)
//        text.setTextColor(Color.WHITE)
//        text.gravity = Gravity.CENTER
//        text.layoutParams = FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,FrameLayout.LayoutParams.MATCH_PARENT)
//        setContentView(text)
//    }
//}