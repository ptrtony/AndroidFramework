package com.sinata.hi_ui.recyclerview

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView

/**
@author cjq
@Date 17/5/2021
@Time 11:49 PM
@Describe:
 */
class HiRefreshRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {


    init {
        addOnScrollListener(object : OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                //当RecyclerView 没有滑动的时候
                if (newState == SCROLL_STATE_IDLE){

                //当RecyclerView拖拽外边缘
                }else if (newState == SCROLL_STATE_DRAGGING){

                //执行动画的时候调用
                }else if (newState == SCROLL_STATE_SETTLING){

                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

            }
        })
    }



}