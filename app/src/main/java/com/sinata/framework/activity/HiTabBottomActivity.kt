package com.sinata.framework.activity

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import com.sinata.framework.R
import com.sinata.framework.logic.MainActivityLogic
import com.sinata.framework.logic.MainActivityLogic.ActivityProvider

class HiTabBottomActivity : AppCompatActivity() , ActivityProvider{
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
}