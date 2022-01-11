package com.sinata.framework.flutter

import android.os.Bundle
import android.view.View
import java.io.Serializable

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/11/27
 */
class GoodsFragment : HiFlutterFragment("nativeView"){

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTitle("收藏模块")
    }
}