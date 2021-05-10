package com.sinata.framework.kotlindemo

import android.app.Activity
import android.content.res.Resources
import android.graphics.*
import android.graphics.drawable.AnimatedImageDrawable
import android.os.Build
import androidx.annotation.NonNull
import androidx.annotation.RequiresApi
import java.io.File

/**
@author cjq
@Date 27/4/2021
@Time 3:41 PM
@Describe:
 */
@RequiresApi(Build.VERSION_CODES.P)
fun createSource(@NonNull res: Resources, resId: Int) {
    val source = ImageDecoder.createSource(res, resId)
    ImageDecoder.decodeDrawable(
        source,
        (ImageDecoder.OnHeaderDecodedListener { decoder, info, source ->

        })
    )

}

@RequiresApi(Build.VERSION_CODES.P)
fun createSource(activity: Activity, filePath: String) {
    val source = ImageDecoder.createSource(activity.assets, filePath)
    val decodeBitmap = ImageDecoder.decodeBitmap(source)

}

@RequiresApi(Build.VERSION_CODES.P)
fun drawRoundBitmap(file: File) {
    val sourceFile = ImageDecoder.createSource(file)
    val drawable = ImageDecoder.decodeDrawable(sourceFile) { decoder, info, source ->
        val path = Path().apply {
            fillType = Path.FillType.EVEN_ODD
        }

        val paint = Paint().apply {
            isAntiAlias = true
            color = Color.TRANSPARENT
            xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC)
        }

        decoder.setPostProcessor { canvas ->
            val width = canvas.width.toFloat()
            val height = canvas.height.toFloat()
            val direction = Path.Direction.CW
            path.addRoundRect(0f, 0f, width, height, 100f, 100f, direction)
            canvas.drawPath(path, paint)
            PixelFormat.TRANSPARENT
        }
    }

}

@RequiresApi(Build.VERSION_CODES.P)
fun startAnimationDrawable(resources:Resources,resId:Int){
    val source = ImageDecoder.createSource(resources,resId)
    val drawable = ImageDecoder.decodeDrawable(source)
    if (drawable is AnimatedImageDrawable){
        drawable.start()
    }
}

