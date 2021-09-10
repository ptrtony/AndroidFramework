package com.sinata.hi_debugtool

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sinata.hi_library.log.utils.HiDisplayUtil
import java.lang.reflect.Method

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 2021/8/22
 */
class DebugToolDialogFragment : AppCompatDialogFragment() {

    private var debugTools = arrayOf(DebugTools::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = dialog?.window?.findViewById<ViewGroup>(android.R.id.content)?:container
        dialog?.window?.setLayout((HiDisplayUtil.getDisplayWidthInPx(view!!.context)*0.7).toInt(),WindowManager.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawableResource(R.drawable.shape_hi_debug_tool)
        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        try{
            val itemDecoration = DividerItemDecoration(view.context,DividerItemDecoration.VERTICAL)
            itemDecoration.setDrawable(ContextCompat.getDrawable(view.context,R.drawable.shape_hi_debug_divider)!!)

            val size = debugTools.size
            val functions = mutableListOf<DebugFunction>()
            for(index in 0 until size){
                val claz = debugTools[index]
                val target = claz.getConstructor().newInstance()
                val declaredMethods = target.javaClass.declaredMethods
                for (method in declaredMethods) {
                    var title = ""
                    var desc = ""
                    var enable = false
                    val annotation = method.getAnnotation(HiDebug::class.java)
                    if (annotation != null){
                        title = annotation.name
                        desc = annotation.desc
                        enable = true
                    }else{
                        method.isAccessible = true
                        title = method.invoke(target) as String
                    }
                    val func = DebugFunction(title,desc,method,enable,target)
                    functions.add(func)
                }
            }
            val recyclerView = RecyclerView(view.context)
            recyclerView.layoutParams = WindowManager.LayoutParams(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT)
            (view as ViewGroup).addView(recyclerView)
            recyclerView.addItemDecoration(itemDecoration)
            recyclerView.layoutManager = LinearLayoutManager(view.context,LinearLayoutManager.VERTICAL,false)
            recyclerView.adapter = DebugToolAdapter(functions)
        }catch (e:Exception){
            e.printStackTrace()
        }

    }


    inner class DebugToolAdapter(val data:List<DebugFunction>):RecyclerView.Adapter<RecyclerView.ViewHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val rootView = layoutInflater.inflate(R.layout.hi_debug_tool_item,parent,false)
            return object:RecyclerView.ViewHolder(rootView){}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val itemTitle = holder.itemView.findViewById<TextView>(R.id.item_title)
            val itemDesc = holder.itemView.findViewById<TextView>(R.id.item_desc)
            val function = data[position]
            itemTitle.text = function.name
            if (TextUtils.isEmpty(function.desc)){
                itemDesc.visibility = View.GONE
            }else{
                itemDesc.visibility = View.VISIBLE
                itemDesc.text = function.desc
            }

            if (function.enable){
                holder.itemView.setOnClickListener {
                    function.invoke()
                }
            }

        }

        override fun getItemCount(): Int {
            return data.size
        }

    }

    data class DebugFunction(
        val name: String,
        val desc: String,
        val method: Method,
        val enable: Boolean,
        val target: Any
    ){
        fun invoke(){
            method.invoke(target)
        }
    }
}