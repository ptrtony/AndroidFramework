package com.sinata.framework.lazy_fragment

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.sinata.framework.R
import com.sinata.framework.lazy_fragment.fragment.LazyViewPager2Activity
import com.sinata.framework.lazy_fragment.fragment.LazyViewPagerActivity

class ViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_pager)

        findViewById<Button>(R.id.view_pager).setOnClickListener {
            startActivity(Intent(this,LazyViewPagerActivity::class.java))
        }

        findViewById<Button>(R.id.view_pager2).setOnClickListener {
            startActivity(Intent(this, LazyViewPager2Activity::class.java))
        }
    }
}