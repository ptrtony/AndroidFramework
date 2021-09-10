package com.sinata.framework.componse

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.semantics.SemanticsProperties.Text
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.android.synthetic.main.hi_refresh_over_view.*

class ComposeDemoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

//        setContent {
//            previewMessageCard()
//        }
    }

//
//    @Composable
//    fun messageCard(name:String){
//        Text(text="Hello $name")
//    }
//
//    @Preview
//    @Composable
//    fun previewMessageCard(){
//        messageCard("Android")
//    }

}