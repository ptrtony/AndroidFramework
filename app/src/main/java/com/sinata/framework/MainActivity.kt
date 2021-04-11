package com.sinata.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import com.google.gson.Gson
import com.sinata.framework.log.*
import com.sinata.framework.ui.bottom.HiTabBottom
import com.sinata.framework.ui.bottom.HiTabBottomInfo

class MainActivity : AppCompatActivity() {
    private var hiViewPrinter: HiViewPrinter? = null
    private var hiFilePrinter: HiFilePrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        hiViewPrinter = HiViewPrinter(this)
//        hiFilePrinter = HiFilePrinter.getInstance(Environment.getStorageDirectory().absolutePath,3600*24)
//        findViewById<Button>(R.id.btn_logger).setOnClickListener {
//            printLog()
//        }
//        hiViewPrinter!!.viewProvider.showFloatingView()
        val tabInfo = HiTabBottomInfo<String>("首页",
            "fonts/iconfont.ttf",
            getString(R.string.if_home),
            null,
            "#ff656667",
            "#ffd44949"
        )
        findViewById<HiTabBottom>(R.id.hi_tab_bottom).setHiTabInfo(tabInfo)
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(hiViewPrinter)
        HiLogManager.getInstance().addPrinter(hiFilePrinter)
        HiLog.log(object : HiLogConfig() {

            override fun includeThread(): Boolean {
                return false
            }
        }, HiLogType.E, "----- ", "55555")
    }
}