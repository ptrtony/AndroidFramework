package com.sinata.framework.hilt

import android.content.Context
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.RelativeLayout
import androidx.core.app.ActivityCompat
import com.sinata.common.ui.view.EmptyView
import com.sinata.framework.R
import com.sinata.hi_library.utils.HiRes
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.android.synthetic.main.activity_search.view.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/10/20
 */

//.用来告诉Hilt这个模块可以被这些组件应用
//.声明该模块中创建的对象d额生命周期

//@InstallIn(ActivityCompat::class)
//@Module
//object HomeModel {
//
//    @Provides
//    @ActivityScoped
//    fun newEmptyView(@ActivityContext context:Context):EmptyView{
//        //@ActivityContext
//        //声明context的类型，此处声明为Activity类型的context
//        //除此之外也可使用@ApplicationContext，声明为Application context
//        val emptyView = EmptyView(context,null)
//        emptyView.setDesc(HiRes.getString(R.string.list_empty_desc))
//        emptyView.setIcon(R.string.list_empty)
//        emptyView.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT,MATCH_PARENT)
//        emptyView.setBackgroundColor(HiRes.getColor(R.color.white))
//        return emptyView
//    }
//}