package com.sinata.hi_ui.recyclerview

import android.content.Context
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.ref.WeakReference
import java.lang.reflect.ParameterizedType
import kotlin.RuntimeException

/**
@author cjq
@Date 17/5/2021
@Time 10:27 PM
@Describe:
 */
class HiAdapter constructor(context: Context) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var recyclerViewRef: WeakReference<RecyclerView>? = null
    private var mContext: Context
    private var mInflater: LayoutInflater
    private var dataSets = arrayListOf<HiDataItem<*, out RecyclerView.ViewHolder>>()
    private var typeArrays = SparseArray<HiDataItem<*, out RecyclerView.ViewHolder>>()

    private var headers = SparseArray<View>()
    private var footers = SparseArray<View>()
    private var BASE_ITEM_TYPE_HEADER = 100000
    private var BASE_ITEM_TYPE_FOOTER = 200000


    init {
        this.mContext = context
        mInflater = LayoutInflater.from(mContext)
    }

    fun addHeaderView(view: View) {
        if (headers.indexOfValue(view) < 0) {
            headers.put(BASE_ITEM_TYPE_HEADER++, view)
            notifyItemInserted(headers.size() - 1)
        }
    }

    fun removeHeaderView(view: View) {
        val indexOfValue = headers.indexOfValue(view)
        if (indexOfValue < 0) return
        headers.removeAt(indexOfValue)
        notifyItemRemoved(indexOfValue)
    }

    fun addFooterView(view: View) {
        if (footers.indexOfValue(view) < 0) {
            footers.put(BASE_ITEM_TYPE_FOOTER++, view)
            notifyItemInserted(itemCount)
        }
    }

    fun removeFooterView(view: View) {
        val indexOfValue = footers.indexOfValue(view)
        if (indexOfValue < 0) return
        footers.removeAt(indexOfValue)
        notifyItemRemoved(getHeaderSize() + getOriginalItemSize() + indexOfValue)
    }

    fun getHeaderSize(): Int {
        return headers.size()
    }

    fun getOriginalItemSize(): Int {
        return dataSets.size
    }

    fun getFooterSize(): Int {
        return footers.size()
    }

    /**
     * 在指定为上添加HiDataItem
     */
    fun addItemAt(index:Int,dataItem:HiDataItem<*,out RecyclerView.ViewHolder>,notify:Boolean){
        if (index >= 0){
            dataSets.add(index,dataItem)
        }else{
            dataSets.add(dataItem)
        }
        val notifyPos = if (index >= 0) index else dataSets.size - 1
        if (notify){
            notifyItemInserted(notifyPos)
        }
        dataItem.setAdapter(this)
    }


    fun addItem(index: Int, item: HiDataItem<*, out RecyclerView.ViewHolder>, notify: Boolean) {
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

    fun addItems(items: List<HiDataItem<*, out RecyclerView.ViewHolder>>, notify: Boolean) {
        val start = dataSets.size
        for (item in items) {
            dataSets.add(item)
        }
        if (notify) {
            notifyItemRangeInserted(start, items.size)
        }
    }

    fun removeItemAt(index: Int): HiDataItem<*, out RecyclerView.ViewHolder>? {
        if (index > 0 && index < dataSets.size) {
            val item = dataSets.removeAt(index)
            notifyItemRemoved(index)
            return item
        }
        return null
    }


    fun removeItem(item: HiDataItem<*, out RecyclerView.ViewHolder>?) {
        if (item != null) {
            val index = dataSets.indexOf(item)
            removeItemAt(index)
        }
    }

    /**
     * 指定刷新，某个item的数据
     */

    fun refreshItem(hiDataItem: HiDataItem<*, out RecyclerView.ViewHolder>) {
        val index = dataSets.indexOf(hiDataItem)
        notifyItemChanged(index)
    }

    /**
     * 以每种item类型的class.hashcode 为该item的itemType
     * 这里把type 存储起来，是为了onCreateViewHolder方法能够为不同类型的item创建不同的viewHolder
     */
    override fun getItemViewType(position: Int): Int {
        if (isHeaderPosition(position)) {
            return headers.keyAt(position)
        }
        if (isFooterPosition(position)) {
            //footer的位置，应该计算一下，position = 6，headerCount = 1 ， footerCount = 1
            val footerPosition = position - getHeaderSize() - getOriginalItemSize()
            return footers.keyAt(footerPosition)
        }

        val itemPosition = position - getHeaderSize()
        val dataItem = dataSets[itemPosition]
        val type = dataItem.javaClass.hashCode()
        if (typeArrays.indexOfKey(type) < 0) {
            typeArrays.put(type, dataItem)
        }
        return type
    }

    private fun isHeaderPosition(position: Int): Boolean {
        return position < headers.size()
    }

    private fun isFooterPosition(position: Int): Boolean {
        return position >= getHeaderSize() + getOriginalItemSize()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (headers.indexOfKey(viewType) >= 0) {
            val view = headers.get(viewType)
            return object : RecyclerView.ViewHolder(view) {}
        }

        if (footers.indexOfKey(viewType) >= 0) {
            val view = footers.get(viewType)
            return object : RecyclerView.ViewHolder(view) {}
        }

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
        javaClass: Class<HiDataItem<*, out RecyclerView.ViewHolder>>,
        view: View?
    ): RecyclerView.ViewHolder {
        val superClass = javaClass.genericSuperclass
        if (superClass is ParameterizedType) {
            val arguments = superClass.actualTypeArguments
            for (argument in arguments) {
                if (argument is Class<*> && RecyclerView::class.java.isAssignableFrom(argument)) {
                    return argument.getDeclaredConstructor(View::class.java)
                        .newInstance(view) as RecyclerView.ViewHolder
                }
            }
        }
        return object : RecyclerView.ViewHolder(view!!) {}
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (isHeaderPosition(position) || isFooterPosition(position)) return
        val itemPosition = position - getHeaderSize();
        val hiItemData = dataSets[itemPosition] as HiDataItem<*,RecyclerView.ViewHolder>
        hiItemData.onBindData(holder, itemPosition)
    }

    //adapter和RecyclerView相关联的时候回调onAttachedToRecyclerView
    /**
     * 为列表上的item，适配网格布局
     */
    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewRef = WeakReference(recyclerView)

        /**
         * 为列表的item 适配网格布局
         */
        val layoutManager = recyclerView.layoutManager
        if (layoutManager is GridLayoutManager) {
            val spanCount = layoutManager.spanCount
            layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                override fun getSpanSize(position: Int): Int {
                    if (isHeaderPosition(position) || isFooterPosition(position)) {
                        return spanCount
                    }
                    val itemPosition = position - getHeaderSize()
                    if (itemPosition < dataSets.size) {
                        val hiDataItem = dataSets[itemPosition]
                        val spanSize = hiDataItem.getSpanSize()
                        return if (spanSize > 0) spanSize else spanCount
                    }
                    return spanCount
                }
            }
        }
    }

    override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
        val recyclerView = getAttachedRecyclerView()
        if (recyclerView != null) {
            //瀑布流的item占比适配
            val position = recyclerView.getChildAdapterPosition(holder.itemView)
            val isHeaderFooter = isHeaderPosition(position) || isFooterPosition(position)
            val itemPosition = position - getHeaderSize()
            val dataItem = getItem(itemPosition) ?: return
            val lp = holder.itemView.layoutParams
            if (lp != null && lp is StaggeredGridLayoutManager.LayoutParams) {
                val manager = recyclerView.layoutManager as StaggeredGridLayoutManager
                if (isHeaderFooter) {
                    lp.isFullSpan = true
                    return
                }
                val spanSize = dataItem.getSpanSize()
                if (spanSize == manager.spanCount) {
                    lp.isFullSpan = true
                }
            }

            dataItem.onViewAttachedToWindow(holder)
        }
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewRef?.clear()
    }

    private fun getItem(position: Int): HiDataItem<*,RecyclerView.ViewHolder>? {
        if (position < 0 || position >= dataSets.size)
            return null
        return dataSets[position] as HiDataItem<*,RecyclerView.ViewHolder>
    }

    open fun getAttachedRecyclerView(): RecyclerView? {
        return recyclerViewRef?.get()
    }

    override fun getItemCount(): Int {
        return getHeaderSize() + getOriginalItemSize() + getFooterSize()
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        val position = holder.adapterPosition
        if (isHeaderPosition(position) || isFooterPosition(position))
            return
        val itemPosition = position - getHeaderSize()
        val dataItem = getItem(itemPosition)?:return
        dataItem.onViewDetachedFromWindow(holder)
    }

    fun clearItems(){
        dataSets.clear()
        notifyDataSetChanged()
    }
}