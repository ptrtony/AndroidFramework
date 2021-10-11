package com.sinata.framework.hi_arch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.sinata.framework.R
import com.sinata.framework.hi_arch.scene2.Scene2Activity
import com.sinata.framework.hi_arch.scene3.Scene3Activity

class SceneActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scene)
    }


    fun onScene2Click(view:View){
        startActivity(Intent(this,Scene2Activity::class.java))
    }

    fun onScene3Click(view:View){
        startActivity(Intent(this,Scene3Activity::class.java))
    }
}