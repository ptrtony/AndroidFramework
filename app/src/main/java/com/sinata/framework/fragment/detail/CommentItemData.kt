package com.sinata.framework.fragment.detail

import android.content.res.ColorStateList
import android.util.SparseArray
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.core.content.ContextCompat
import com.google.android.material.chip.Chip
import com.google.android.material.shape.ShapeAppearanceModel
import com.sinata.common.ui.view.loadCircleUrl
import com.sinata.framework.R
import com.sinata.framework.model.DetailModel
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_ui.recyclerview.HiDataItem
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.layout_detail_item_comment.*
import kotlin.math.min

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/9/16
 */
class CommentItemData(val detailModel: DetailModel) : HiDataItem<DetailModel, HiViewHolder>() {

    private var cacheView = SparseArray<View>()


    override fun getItemLayoutRes(): Int {
        return R.layout.layout_detail_item_comment
    }


    override fun onBindData(holder: HiViewHolder, position: Int) {
        val context = holder.itemView.context ?: return
        holder.title_comment.text = detailModel.commentCountTitle
        val commentTags = detailModel.commentTags
        if (!commentTags.isNullOrBlank()) {
            val tagsArray = commentTags.split(" ")
            if (!tagsArray.isNullOrEmpty()) {
                for (index in tagsArray.indices) {
                    val chipLabel = if (index < holder.chip_group.childCount) {
                        holder.chip_group.getChildAt(index) as Chip
                    } else {
                        val chipLabel = Chip(context)
                        chipLabel.isEnabled = false
                        chipLabel.isCheckable = false
                        chipLabel.isCheckedIconVisible = false
                        chipLabel.chipBackgroundColor = ColorStateList.valueOf(
                            ContextCompat.getColor(
                                context,
                                R.color.color_9fa
                            )
                        )
                        chipLabel.gravity = Gravity.CENTER
                        chipLabel.textSize = 14f
                        chipLabel.setTextColor(ContextCompat.getColor(context, R.color.color_999))
                        chipLabel.isChipIconVisible = false
                        val shapeAppearanceModel = ShapeAppearanceModel()
                        shapeAppearanceModel.withCornerSize(HiDisplayUtil.dp2Px(4f).toFloat())
                        chipLabel.shapeAppearanceModel = shapeAppearanceModel
                        holder.chip_group.addView(chipLabel)
                        chipLabel
                    }
                    chipLabel.text = tagsArray[index]
                }
            }
        }

        detailModel.commentModels?.let {
            for (index in 0..min(it.size - 1,3)){
                val commentModel = it[index]
                val commentContainer = holder.comment_container
                val commentView = if (index < commentContainer.childCount){
                    commentContainer.getChildAt(index)
                }else{
                    val comment = LayoutInflater.from(context)
                        .inflate(R.layout.layout_detail_comment_item, holder.comment_container, false)
                    holder.comment_container.addView(comment)
                    comment
                }
                val userAvatar = findViewById<ImageView>(R.id.user_avatar, commentView)
                val userName = findViewById<TextView>(R.id.user_name, commentView)
                val commentContent = findViewById<TextView>(R.id.comment_content, commentView)
                userAvatar.loadCircleUrl(commentModel.avatar)
                userName.text = commentModel.nickname
                commentContent.text = commentModel.content
                holder.comment_container.addView(commentView)
            }
        }
    }


    fun <T : View> findViewById(@IdRes id: Int, rootView: View): T {
        var view = cacheView[id]
        if (view == null) {
            view = rootView.findViewById<T>(id)
            cacheView.put(id, view)
        }
        return view as T
    }


}