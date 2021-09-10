package com.sinata.framework.biz.goods

import android.os.Bundle
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.framework.R
import com.sinata.hi_library.utils.HiStatusBar
import kotlinx.android.synthetic.main.activity_goods_list.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 21/8/2021
 */


@Route(path = "/goods/list")
class GoodsListActivity : HiBaseActivity() {

    @JvmField
    @Autowired(name = "categoryTitle")
    var categoryTitle: String? = null

    @JvmField
    @Autowired(name = "categoryId")
    var categoryId: String? = null

    @JvmField
    @Autowired(name = "subcategoryId")
    var subcategoryId: String? = null

    private val FRAGMENT_TAG = "GOODS_LIST_FRAGMENT"

    override fun onCreate(savedInstanceState: Bundle?) {
        HiStatusBar.setStatusBar(this, true, translucent = false)
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        setContentView(R.layout.activity_goods_list)

        action_back.setOnClickListener { onBackPressed() }
        category_title.text = categoryTitle

        var fragment = supportFragmentManager.findFragmentByTag(FRAGMENT_TAG)
        if (fragment == null) {
            fragment = GoodsListFragment.newInstance(categoryId, subcategoryId)
        }
        val beginTransaction = supportFragmentManager.beginTransaction()
        if (!fragment.isAdded){
            beginTransaction.add(R.id.container, fragment, FRAGMENT_TAG)
        }
        beginTransaction.show(fragment).commitNowAllowingStateLoss()

    }
}