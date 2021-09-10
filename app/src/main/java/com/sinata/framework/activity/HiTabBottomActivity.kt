package com.sinata.framework.activity

import android.os.Bundle
import android.os.PersistableBundle
import android.view.KeyEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.sinata.framework.BuildConfig
import com.sinata.framework.R
import com.sinata.framework.logic.MainActivityLogic
import com.sinata.framework.logic.MainActivityLogic.ActivityProvider
import com.sinata.hi_debugtool.DebugToolDialogFragment
import com.sinata.hi_debugtool.DebugTools

class HiTabBottomActivity : AppCompatActivity(), ActivityProvider {
    private lateinit var mainActivityLogic: MainActivityLogic
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_tab_bottom)
        mainActivityLogic = MainActivityLogic(this, savedInstanceState)
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {
        super.onSaveInstanceState(outState, outPersistentState)
        mainActivityLogic.onSaveInstanceState(outState)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN) {
            if (BuildConfig.DEBUG) {
                val clazz = Class.forName("com.sinata.hi_debugtool.DebugToolDialogFragment")
                val debugTools = clazz.getDeclaredConstructor().newInstance() as DebugToolDialogFragment
                debugTools.show(supportFragmentManager,"debug_tools")
            }
        }
        return super.onKeyDown(keyCode, event)
    }
}