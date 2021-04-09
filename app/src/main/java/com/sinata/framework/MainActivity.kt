package com.sinata.framework

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.google.gson.Gson
import com.sinata.framework.log.*

class MainActivity : AppCompatActivity() {
    private var hiViewPrinter: HiViewPrinter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hiViewPrinter = HiViewPrinter(this)

        findViewById<Button>(R.id.btn_logger).setOnClickListener {
            printLog()
        }
        hiViewPrinter!!.viewProvider.showFloatingView()
    }

    private fun printLog() {
        HiLogManager.getInstance().addPrinter(hiViewPrinter)
        HiLog.log(object : HiLogConfig() {

            override fun includeThread(): Boolean {
                return false
            }
        }, HiLogType.E, "----- ", "55555")
    }
}