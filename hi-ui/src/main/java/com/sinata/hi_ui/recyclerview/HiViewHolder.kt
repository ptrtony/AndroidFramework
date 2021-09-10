package com.sinata.hi_ui.recyclerview

import android.util.SparseArray
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 7/8/2021
 */
class HiViewHolder(val view: View) : RecyclerView.ViewHolder(view), LayoutContainer {

    private var cacheView = SparseArray<View>()
    override val containerView: View
        get() = view


    fun <T> findViewById(resId: Int): T {
        var view = cacheView.get(resId)
        if (view == null){
            view = itemView.findViewById<View>(resId)
            cacheView.put(resId,view)
        }
        return view as T
    }

}