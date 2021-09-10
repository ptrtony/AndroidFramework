package com.sinata.framework.arouter

import android.content.Context
import com.alibaba.android.arouter.facade.Postcard
import com.alibaba.android.arouter.facade.annotation.Interceptor
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.facade.service.DegradeService
import com.alibaba.android.arouter.launcher.ARouter

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 4/6/2021
 */

@Route(path = "/degrade/detail/service")
class DegradeServiceImpl : DegradeService {

    override fun init(context: Context?) {

    }

    override fun onLost(context: Context?, postcard: Postcard?) {
        ARouter.getInstance().build("/profile/unknown").greenChannel().navigation()
    }
}