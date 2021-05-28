package com.sinata.framework.lazy_fragment.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.sinata.framework.R
import com.sinata.framework.lazy_fragment.ViewPagerAdapter

class LazyViewPagerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lazy_view_pager)

        val fragments = mutableListOf<Fragment>()
        fragments.add(FirstFragment())
        fragments.add(SecondFragment())

        val viewPager = findViewById<ViewPager>(R.id.view_pager)
        viewPager.adapter = ViewPagerAdapter(supportFragmentManager,fragments)
    }
}