package com.sinata.framework.arouter

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sinata.framework.R
import kotlinx.android.synthetic.main.layout_global_degrade.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/7/2021
 */

/**
 * 全局统一错误页
 */

@Route(path = "/degrade/global/activity")
class DegradeGlobalActivity : AppCompatActivity() {

    @JvmField
    @Autowired
    var degrade_title: String? = null

    @JvmField
    @Autowired
    var degrade_desc: String? = null

    @JvmField
    @Autowired
    var degrade_action: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.layout_global_degrade)
        if (degrade_title != null) {
            empty_view.setTitle(degrade_title!!)
        }

        if (degrade_desc != null) {
            empty_view.setDesc(degrade_desc!!)
        }

        if (degrade_action!=null){
            empty_view.setHelpAction(listener = View.OnClickListener{
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse("degrade_action"))
                startActivity(intent)
            })
        }

        action_back.setOnClickListener {
            finish()
        }

    }
}