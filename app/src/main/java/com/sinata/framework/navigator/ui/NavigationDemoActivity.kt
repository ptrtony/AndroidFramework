package com.sinata.framework.navigator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.sinata.framework.R
import com.sinata.framework.navigator.utils.NavUtil

class NavigationDemoActivity : AppCompatActivity() {
    var navController:NavController?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation_demo)
        val navView = findViewById<BottomNavigationView>(R.id.nav_view)
//        val navHostFragment = findNavController(R.id.nav_host_fragment)
        navController = findNavController(R.id.nav_host_fragment)
        val fragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        NavUtil.buildNavGraph(this,fragment?.childFragmentManager,navController,R.id.nav_host_fragment)
        navView.setupWithNavController(navController!!)
    }
}