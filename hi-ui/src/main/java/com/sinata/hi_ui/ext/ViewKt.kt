package com.sinata.hi_ui.ext

import android.graphics.Color
import android.view.View

fun View.GONE(){
    this.visibility = View.GONE
}

fun View.VISIBLE(){
    this.visibility = View.VISIBLE
}

fun Any.getTextView(): Int {
    return if (this is String) { Color.parseColor(this) } else { this as Int }
}