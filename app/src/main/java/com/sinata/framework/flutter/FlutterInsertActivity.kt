package com.sinata.framework.flutter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sinata.framework.R

class FlutterInsertActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flutter_insert)
    }



    class MFlutterFragment(moduleName:String):HiFlutterFragment(moduleName){
        override fun onDestroy() {
            super.onDestroy()
            HiFlutterCacheManager.instance?.destroyCached(moduleName)
        }
    }
}