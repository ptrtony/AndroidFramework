package com.sinata.framework.recyclerview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.framework.R

class RecyclerViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        val rvDemo = findViewById<RecyclerView>(R.id.rv_demo)
        rvDemo.adapter = MyAdapter()

        rvDemo.layoutManager = LinearLayoutManager(this)

    }

    inner class MyAdapter :RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun getItemCount(): Int {
            return 0
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            return MyViewHolder(parent)
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        }

        inner class MyViewHolder constructor(view:View): RecyclerView.ViewHolder(view){

        }

    }
}