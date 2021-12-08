package com.sinata.framework.flutter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.sinata.framework.R
import kotlinx.android.synthetic.main.activity_flutter_demo.*

class FlutterDemoActivity : AppCompatActivity() {
    private lateinit var beginTransaction: FragmentTransaction
    private var fragments = mutableListOf<Fragment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter_demo)
        fragments.add(GoodsFragment())
        fragments.add(RecommendFragment())
        beginTransaction = supportFragmentManager.beginTransaction()
        beginTransaction
            .add(R.id.frameLayout,fragments[0])
            .add(R.id.frameLayout,fragments[1])
        btn_collect.setOnClickListener {
            navigation(HiFlutterCacheManager.MODULE_NAME_FAVORITE)
        }


        btn_recommend.setOnClickListener {
            navigation(HiFlutterCacheManager.MODULE_NAME_RECOMMEND)
        }



    }


    fun navigation(type:String){
        beginTransaction.hide(if (type == HiFlutterCacheManager.MODULE_NAME_FAVORITE) fragments[0] else fragments[1])
        beginTransaction.show(if (type == HiFlutterCacheManager.MODULE_NAME_FAVORITE) fragments[1] else fragments[0])
        beginTransaction.commitNowAllowingStateLoss()
    }
}