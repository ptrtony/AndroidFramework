package com.sinata.framework.camerawater

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.provider.MediaStore
import com.sinata.framework.R
import com.sinata.hi_library.camera.PhotoUtil
import kotlinx.android.synthetic.main.activity_water.*
import rx.functions.Action1


class WaterActivity : AppCompatActivity() {

    @SuppressLint("HandlerLeak")
    private var mHandler = object: Handler(){
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water)
        btn.setOnClickListener {
            PhotoUtil.getInstance(this).checkPermission()
        }
//        mHandler.obtainMessage()
//        mHandler.sendMessage()
//        mHandler.post()


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PhotoUtil.getInstance(this).onRequestPermissionsResult(requestCode,permissions,grantResults)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        PhotoUtil.getInstance(this).onActivityResult(requestCode,resultCode,data,
            Action1<Bitmap> {
                gallery.setImageBitmap(it)
            })
    }
}