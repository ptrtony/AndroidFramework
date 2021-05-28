package com.sinata.hi_ui.recyclerview

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.reflect.ParameterizedType
import kotlin.RuntimeException

/**
@author cjq
@Date 17/5/2021
@Time 10:27 PM
@Describe:
 */
class HiAdapter constructor(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var mContext: Context
    private var mInflater: LayoutInflater
    private var dataSets = arrayListOf<HiDataItem<*, RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, RecyclerView.ViewHolder>>()

    init {
        this.mContext = context
        mInflater = LayoutInflater.from(mContext)
    }


    fun addItem(index: Int, item: HiDataItem<*, RecyclerView.ViewHolder>, notify: Boolean) {
        if (index > 0) {
            dataSets.add(index, item)
        } else {
            dataSets.add(item)
        }
        val notifyPos = if (index > 0) index else dataSets.size
        if (notify) {
            notifyItemInserted(notifyPos)
        }
    }

    fun addItems(items: List<HiDataItem<*, RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItemAt(index: Int): HiDataItem<*, RecyclerView.ViewHolder>? {
        if (index > 0 && index < dataSets.size) {
            val item = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return item
        }
        return null
    }


    fun removeItem(item: HiDataItem<*, *>?) {
        if (item != null) {
            val index = dataSets.indexOf(item)
            removeItemAt(index)
        }
    }

    override fun getItemViewType(position: Int): Int {
        val dataItem = dataSets[position]
        val type = dataItem.javaClass.hashCode()
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val dataItem = typeArrays[viewType]
        var view = dataItem.getItemView(parent)
        if (view == null) {
            val layoutRes = dataItem.getItemLayoutRes()
            if (layoutRes < 0) {
                RuntimeException("dataItem: ${dataItem.javaClass.name} override getItemView or getItemLayoutRes")
            }
            view = mInflater.inflate(layoutRes, parent, false)
        }

        return createViewHolderInternal(dataItem.javaClass, view)
    }

    private fun createViewHolderInternal(
        javaClass: Class<HiDataItem<*, RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType){
            val arguments = superClass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView::class.java.isAssignableFrom(argument)){
                    return argument.getDeclaredConstructor(View::class.java).newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        return object:RecyclerView.ViewHolder(view!!){}
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val hiItemData = dataSets[position]
        hiItemData.onBindData(holder,position)
    }

    //adapter和RecyclerView相关联的时候回调onAttachedToRecyclerView
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager){
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object:GridLayoutManager.SpanSizeLookup(){
                override fun getSpanSize(position: Int): Int {
                    if (position < dataSets.size){
                        val hiDataItem = dataSets[position]
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize > 0) spanSize else spanCount
                    }
                    return spanCount
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return dataSets.size
    }

    fun  refreshItem(hiDataItem: HiDataItem<*, *>) {
        val index = dataSets.indexOf(hiDataItem)
        notifyItemChanged(index)
    }


}