package com.sinata.framework.flutter

import android.os.Bundle
import android.view.View

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/11/27
 */
class RecommendFragment : HiFlutterFragment(HiFlutterCacheManager.MODULE_NAME_RECOMMEND) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("推荐模块")
    }

}