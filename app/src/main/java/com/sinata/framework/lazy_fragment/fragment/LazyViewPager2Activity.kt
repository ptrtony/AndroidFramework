package com.sinata.framework.lazy_fragment.fragment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.sinata.framework.R
import com.sinata.framework.lazy_fragment.ViewPager2Adapter

class LazyViewPager2Activity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lazy_view_pager2)

        val fragments = mutableListOf<Fragment>()
        fragments.add(FirstFragment())
        fragments.add(SecondFragment())
        val viewPager2 = findViewById<ViewPager2>(R.id.view_pager2)
        viewPager2.orientation = ViewPager2.ORIENTATION_HORIZONTAL
        viewPager2.adapter = ViewPager2Adapter(this,fragments)

    }
}