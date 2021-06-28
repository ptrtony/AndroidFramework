package com.sinata.framework.coroutines

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.whenCreated
import androidx.lifecycle.whenResumed
import androidx.lifecycle.whenStarted
import com.sinata.framework.R
import kotlinx.coroutines.launch

class CoroutinesActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<TextView>(R.id.tv).setOnClickListener {
            lifecycleScope.launch {

            }

            lifecycleScope.launchWhenCreated {
                whenCreated {

                }

                whenResumed {

                }

                whenStarted {

                }
            }
        }
    }

}