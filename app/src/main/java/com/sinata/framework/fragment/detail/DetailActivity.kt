package com.sinata.framework.fragment.detail

import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.sinata.common.ui.component.HiBaseActivity
import com.sinata.common.ui.view.EmptyView
import com.sinata.framework.BuildConfig
import com.sinata.framework.R
import com.sinata.framework.arouter.HiRoute
import com.sinata.framework.fragment.home.GoodsItem
import com.sinata.framework.model.DetailModel
import com.sinata.framework.model.GoodsModel
import com.sinata.framework.model.selectPrice
import com.sinata.hi_library.utils.HiStatusBar
import com.sinata.hi_library.utils.ToastUtils
import com.sinata.hi_ui.recyclerview.HiAdapter
import com.sinata.hi_ui.recyclerview.HiDataItem
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.android.synthetic.main.activity_detail.view.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:

@author jingqiang.cheng
@date 2021/9/14
 */

@Route(path = "/detail/main")
class DetailActivity : HiBaseActivity() {

    private lateinit var viewModel: DetailViewModel
    private var emptyView: EmptyView? = null

    @JvmField
    @Autowired
    var goodsId: String? = null

    @JvmField
    @Autowired
    var goodsModel: GoodsModel? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        HiStatusBar.setStatusBar(this, true, Color.TRANSPARENT, true)
        HiRoute.inject(this)

        if (BuildConfig.DEBUG && !TextUtils.isEmpty(goodsId)) {
            error("goodsId is not must be nul")
        }

        setContentView(R.layout.activity_detail)
        initView()
        preBindData()
        viewModel = DetailViewModel.get(goodsId, this)
        viewModel.queryDetailPage().observe(this, Observer { detailModel ->
            if (detailModel != null) {
                bindData(detailModel)
            } else {
                showEmptyView()
            }
        })
    }


    private fun preBindData() {
        if (goodsModel == null) return
        val hiAdapter = recycler_view.adapter as HiAdapter
        hiAdapter.addItemAt(
            0,
            HeaderItemData(
                goodsModel!!.sliderImages,
                selectPrice(goodsModel!!.groupPrice, goodsModel!!.marketPrice) ?: "",
                goodsModel!!.completedNumText,
                goodsModel!!.goodsName
            ),
            true
        )

    }

    private fun bindData(detailModel: DetailModel) {
        recycler_view.visibility = View.VISIBLE
        emptyView?.visibility = View.GONE
        val hiAdapter = recycler_view.adapter as HiAdapter
        val hiDataItems = mutableListOf<HiDataItem<*, out RecyclerView.ViewHolder>>()
        //头部轮播
        hiDataItems.add(
            HeaderItemData(
                detailModel.sliderImages,
                selectPrice(detailModel.groupPrice, detailModel.marketPrice) ?: "",
                detailModel.completedNumText,
                detailModel.goodsName
            )
        )
        //评论item
        hiDataItems.add(CommentItemData(detailModel))
        //店铺模块
        hiDataItems.add(ShopItem(detailModel))
        //商品描述
        hiDataItems.add(GoodsAttrItem(detailModel))
        //图库
        detailModel.gallery?.forEach {
            hiDataItems.add(GalleryItem(it))
        }
        detailModel.similarGoods?.let {
            hiDataItems.add(SimilarItem())
            it.forEach { goodsModel ->
                hiDataItems.add(GoodsItem(goodsModel, false))
            }
        }
        hiAdapter.addItems(hiDataItems, true)
        updateFavoriteActionFace(detailModel.isFavorite)
        updateOrderActionFace(detailModel)
    }


    private fun updateFavoriteActionFace(favorite: Boolean) {
        action_favorite.setOnClickListener {
            toggleFavorite()
        }

        action_favorite.setTextColor(ContextCompat.getColor(this,if (favorite) R.color.color_dd2 else R.color.color_999))

    }


    private fun updateOrderActionFace(detailModel: DetailModel) {
        action_order.text = selectPrice(detailModel.groupPrice,detailModel.marketPrice) + "\n立即购买"

    }

    private fun toggleFavorite() {
        if (!com.sinata.framework.account.AccountManager.isLogin()) {
            com.sinata.framework.account.AccountManager.login(this, Observer { successBoolean ->
                if (successBoolean) {
                    toggleFavorite()
                }
            })
        } else {
            action_favorite.isClickable = false
            viewModel.toggleFavorite().observe(this, Observer { success ->
                if (success != null) {
                    //网络成功
                    updateFavoriteActionFace(success)
                    val message = if (success) "收藏成功" else "取消收藏"
                    ToastUtils.showShortToast(message)
                } else {
                    //网络失败
                }

                action_favorite.isClickable = true
            })
        }
    }

    private fun showEmptyView() {
        if (emptyView == null) {
            emptyView = EmptyView(this, null, 0)
            emptyView?.apply {
                setIcon(R.string.if_empty3)
                setDesc(getString(R.string.list_empty_desc))
                layoutParams = ConstraintLayout.LayoutParams(
                    ConstraintLayout.LayoutParams.WRAP_CONTENT,
                    ConstraintLayout.LayoutParams.WRAP_CONTENT
                )
                setBackgroundColor(Color.WHITE)
                setButton(getString(R.string.list_empty_action), View.OnClickListener {
                    viewModel.queryDetailPage()
                })
            }
            root_container.addView(emptyView)
        }

        recycler_view.visibility = View.GONE
        emptyView?.visibility = View.VISIBLE
    }

    private fun initView() {
        recycler_view.apply {
            layoutManager = GridLayoutManager(this@DetailActivity, 2)
            adapter = HiAdapter(this@DetailActivity)
            addOnScrollListener(TitleScrollListener(resources.getDimension(R.dimen.dp_70)){ color ->
                title_bar.setBackgroundColor(color)
            })
        }
    }


}