package com.sinata.framework.lifecycleDemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.sinata.framework.R
import com.sinata.hi_library.utils.ActivityManager

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        findViewById<TextView>(R.id.tv_top_activity)
            .text = "当前任务栈栈顶的activity:${ActivityManager.instance.topActivity?.javaClass?.simpleName}"
    }
}