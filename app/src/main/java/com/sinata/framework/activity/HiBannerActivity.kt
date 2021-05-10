package com.sinata.framework.activity

import android.os.Bundle
import android.widget.ImageView
import android.widget.Switch
import android.widget.TextView
import com.bumptech.glide.Glide
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.framework.R
import com.sinata.framework.entity.BannerMo
import com.sinata.hi_library.log.HiLog
import com.sinata.hi_ui.banner.HiBanner
import com.sinata.hi_ui.banner.indicator.HiNumberIndicator

class HiBannerActivity : HiBaseActivity() {
    private var urls = arrayOf(
        "https://www.devio.org/img/beauty_camera/beauty_camera1.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera3.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera4.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera5.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera2.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera6.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera7.jpg",
        "https://www.devio.org/img/beauty_camera/beauty_camera8.jpeg"
    )

    private lateinit var banner:HiBanner
    private lateinit var tvSwitch:TextView
    private lateinit var autoPlay:Switch
    private var isAutoPlay:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_hi_banner)
        banner = findViewById(R.id.banner)
        tvSwitch = findViewById(R.id.tv_switch)
        autoPlay = findViewById(R.id.auto_play)
        initView(isAutoPlay)
        autoPlay.setOnCheckedChangeListener { buttonView, isChecked ->
            isAutoPlay = isChecked
            initView(isAutoPlay)
        }
    }


    private fun initView(isAuoPlay:Boolean){
        val mos :MutableList<BannerMo> = arrayListOf()
        for (i in urls.indices){
            val mo = BannerMo()
            mo.url = urls[i]
            mos.add(mo)
        }
        banner.setAutoPlay(isAuoPlay)
        banner.setIntervalTime(2000)
        banner.setHiIndicator(
            HiNumberIndicator(
                this
            )
        )
        banner.setBannerData(R.layout.banner_item_layout,mos)
        banner.setBindAdapter { viewHolder, mo, position ->
            val imageView:ImageView = viewHolder.findViewById(R.id.iv_image)
            Glide.with(HiBannerActivity@this).load(mo.url).into(imageView)
            val textView:TextView= viewHolder.findViewById(R.id.tv_title)
            textView.text = mo.url
            HiLog.dt("----position:",position.toString() + "url:" + urls[position])
        }
    }

}