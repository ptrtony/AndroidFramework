package com.sinata.framework.hi_arch.scene3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.sinata.framework.R
import com.sinata.framework.databinding.ActivityScene3Binding

class Scene3Activity : AppCompatActivity() {
    private val TAG = "Scene3Activity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = DataBindingUtil.setContentView<ActivityScene3Binding>(this, R.layout.activity_scene3)
        val provider = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory())
        val model = provider.get(HomeViewModel::class.java)
        model.userData.observe(this, Observer {
            binding.user = it
        })

        binding.address.addTextChangedListener {
            Log.e(TAG,"onTextChange: " + model.userData.value?.address)
        }

    }
}