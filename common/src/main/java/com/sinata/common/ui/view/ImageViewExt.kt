package com.sinata.common.ui.view

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.sinata.hi_library.utils.HiViewUtil

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company


@author jingqiang.cheng
@date 22/7/2021
 */
fun ImageView.loadUrl(url: String) {
    if (HiViewUtil.isActivityDestroy(context)) return
    Glide.with(context).load(url).into(this)
}


fun ImageView.loadUrl(url:String,callback:(Drawable) -> Unit){
    if (HiViewUtil.isActivityDestroy(context)) return
    Glide.with(context).load(url).into(object:CustomTarget<Drawable>(){
        override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
            callback(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {

        }

    })
}

fun ImageView.loadCircleUrl(url: String) {
    if (HiViewUtil.isActivityDestroy(context)) return
    Glide.with(context).load(url).transform(CenterCrop()).into(this)
}




fun ImageView.loadRoundCornerUrl(url: String, corner: Int) {
    if (HiViewUtil.isActivityDestroy(context)) return
    Glide.with(context).load(url).transform(CenterCrop(), RoundedCorners(corner)).into(this)
}

fun ImageView.loadCircleBorder(
    url: String,
    borderWidth: Float = 0f,
    borderColor: Int = Color.WHITE
) {
    if (HiViewUtil.isActivityDestroy(context)) return
    Glide.with(context).load(url).transform(CircleBorderTransform(borderWidth, borderColor))
        .into(this)
}

class CircleBorderTransform(val borderWidth: Float, borderColor: Int) : CircleCrop() {

    private var borderPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        borderPaint.color = borderColor
        borderPaint.style = Paint.Style.FILL_AND_STROKE
        borderPaint.strokeWidth = borderWidth
    }

    override fun transform(
        pool: BitmapPool,
        toTransform: Bitmap,
        outWidth: Int,
        outHeight: Int
    ): Bitmap {
        val transform = super.transform(pool, toTransform, outWidth, outHeight)
        val canvas = Canvas(transform)
        val halfWidth = outWidth / 2f
        val halfHeight = outHeight / 2f
        canvas.drawCircle(halfWidth, halfHeight, Math.min(halfWidth, halfHeight) - borderWidth/2f, borderPaint)
        canvas.setBitmap(null)
        return transform
    }
}
