package com.sinata.hi_ui.ext

import android.graphics.Color
import android.view.View
import android.widget.Toast
import com.sinata.hi_library.utils.AppGlobal
import java.time.Duration

fun View.GONE(){
    this.visibility = View.GONE
}

fun View.VISIBLE(){
    this.visibility = View.VISIBLE
}

fun Any.getTextView(): Int {
    return if (this is String) { Color.parseColor(this) } else { this as Int }
}

fun <T> T.showToast(message:String,duration: Int = Toast.LENGTH_SHORT) = Toast.makeText(AppGlobal.get(),message,duration).show()