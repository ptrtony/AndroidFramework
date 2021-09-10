package com.sinata.hi_ui.slider

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.sinata.hi_ui.R
import com.sinata.hi_ui.recyclerview.HiViewHolder
import kotlinx.android.synthetic.main.hi_slider_menu_item.view.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:company
@author jingqiang.cheng
@date 19/8/2021
 */
class HiSliderView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private var menuItemAttr: MenuItemAttr
    private val MENU_WIDTH = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 100f)
    private val MENU_HEIGHT = applyUnit(TypedValue.COMPLEX_UNIT_DIP, 45f)
    private val MENU_TEXT_SIZE = applyUnit(TypedValue.COMPLEX_UNIT_SP, 14f)
    private val TEXT_COLOR_NORMAL = Color.parseColor("#666666")
    private val TEXT_COLOR_SELECT = Color.parseColor("#DD3127")

    private val BG_COLOR_NORMAL = Color.parseColor("#F7F8F9")
    private val BG_COLOR_SELECT = Color.parseColor("#FFFFFF")

    private val MENU_ITEM_LAYOUT_RESOURCE_ID = R.layout.hi_slider_menu_item
    private val CONTENT_ITEM_LAYOUT_RESOURCE_ID = R.layout.hi_slider_content_item

    val menuView = RecyclerView(context)
    val contentView = RecyclerView(context)

    init {
        menuItemAttr = parseMenuItemAttr(attrs)
        orientation = HORIZONTAL

        menuView.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
        menuView.overScrollMode = View.OVER_SCROLL_NEVER
        menuView.itemAnimator = null

        contentView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        contentView.overScrollMode = View.OVER_SCROLL_NEVER
        contentView.itemAnimator = null

        addView(menuView)
        addView(contentView)
    }


    fun bindMenuView(
        layoutRes: Int = MENU_ITEM_LAYOUT_RESOURCE_ID,
        itemCount: Int,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemClick: (HiViewHolder, Int) -> Unit
    ) {
        menuView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        menuView.adapter = MenuAdapter(layoutRes, itemCount, onBindView, onItemClick)
    }

    fun bindContentView(
        layoutRes:Int = CONTENT_ITEM_LAYOUT_RESOURCE_ID,
        itemCount: Int,
        decoration:RecyclerView.ItemDecoration?,
        layoutManager:RecyclerView.LayoutManager,
        onBindView: (HiViewHolder, Int) -> Unit,
        onItemClick: (HiViewHolder, Int) -> Unit
    ){
        if (contentView.layoutManager == null){
            contentView.layoutManager = layoutManager
            contentView.adapter = ContentAdapter(layoutRes)
            decoration?.let {
                contentView.addItemDecoration(it)
            }
        }

        val contentAdapter = contentView.adapter as ContentAdapter
        contentAdapter.update(itemCount,onBindView,onItemClick)
        contentAdapter.notifyDataSetChanged()
        contentView.scrollToPosition(0)
    }

    inner class ContentAdapter(val layoutRes: Int) :RecyclerView.Adapter<HiViewHolder>(){

        private lateinit var onItemClick: (HiViewHolder, Int) -> Unit
        private lateinit var onBindView: (HiViewHolder, Int) -> Unit
        private var count: Int = 0

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes,parent,false)
            val remainSpace = width - paddingLeft - paddingRight - menuItemAttr.width
            val layoutManager = (parent as RecyclerView).layoutManager
            var spanCount = 0
            if (layoutManager is GridLayoutManager){
                spanCount = layoutManager.spanCount
            }else if (layoutManager is StaggeredGridLayoutManager){
                spanCount = layoutManager.spanCount
            }
            if (spanCount > 0){
                val itemWidth = remainSpace / spanCount
                itemView.layoutParams = RecyclerView.LayoutParams(itemWidth,itemWidth)
            }
            return HiViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            onBindView(holder,position)
            holder.itemView.setOnClickListener {
                onItemClick(holder,position)
            }
        }

        override fun getItemCount(): Int {
            return count
        }

        fun update(
            itemCount: Int,
            onBindView: (HiViewHolder, Int) -> Unit,
            onItemClick: (HiViewHolder, Int) -> Unit
        ) {
            this.count = itemCount
            this.onBindView = onBindView
            this.onItemClick = onItemClick
        }
    }

    inner class MenuAdapter(
        val layoutRes: Int,
        val count: Int,
        val onBindView: (HiViewHolder, Int) -> Unit,
        val onItemClick: (HiViewHolder, Int) -> Unit
    ) : RecyclerView.Adapter<HiViewHolder>() {

        private var currentSelectIndex = 0
        private var lastSelectIndex = 0
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HiViewHolder {
            val itemView = LayoutInflater.from(context).inflate(layoutRes, parent, false)
            val params = RecyclerView.LayoutParams(menuItemAttr.width, menuItemAttr.height)
            itemView.layoutParams = params
            itemView.setBackgroundColor(menuItemAttr.normalBackgroundColor)
            itemView.findViewById<TextView>(R.id.menu_item_title)
                ?.setTextColor(menuItemAttr.textColor)
            itemView.findViewById<ImageView>(R.id.menu_item_indicator)
                ?.setImageDrawable(menuItemAttr.indicator)
            return HiViewHolder(itemView)
        }

        override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
            holder.itemView.setOnClickListener {
                currentSelectIndex = position
                notifyItemChanged(currentSelectIndex)
                notifyItemChanged(lastSelectIndex)
            }

            //applyItemAttr
            if (currentSelectIndex == position) {
                onItemClick(holder, position)
                lastSelectIndex = currentSelectIndex
            }
            applyItemAttr(holder, position)
            onBindView(holder, position)
        }

        override fun getItemCount(): Int {
            return count
        }


        private fun applyItemAttr(holder: RecyclerView.ViewHolder, position: Int) {
            val selected = position == currentSelectIndex
            val indicatorView: ImageView? = holder.itemView.menu_item_indicator
            val titleView: TextView? = holder.itemView.menu_item_title

            indicatorView?.visibility = if (selected) View.VISIBLE else View.GONE
            titleView?.textSize = if (selected) applyUnit(
                TypedValue.COMPLEX_UNIT_PX,
                menuItemAttr.selectTextSize.toFloat()
            ).toFloat()
            else applyUnit(TypedValue.COMPLEX_UNIT_PX, menuItemAttr.textSize.toFloat()).toFloat()
            holder.itemView.setBackgroundColor(if (selected) menuItemAttr.selectBackgroundColor else menuItemAttr.normalBackgroundColor)

        }

    }


    private fun parseMenuItemAttr(attrs: AttributeSet?): MenuItemAttr {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.HiSliderView)
        val menuItemWidth =
            typedArray.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemWidth, MENU_WIDTH)
        val menuItemHeight =
            typedArray.getDimensionPixelOffset(R.styleable.HiSliderView_menuItemHeight, MENU_HEIGHT)
        val menuItemTextSize = typedArray.getDimensionPixelOffset(
            R.styleable.HiSliderView_menuItemTextSize,
            MENU_TEXT_SIZE
        )
        val menuItemSelectTextSize = typedArray.getDimensionPixelOffset(
            R.styleable.HiSliderView_menuItemSelectTextSize,
            MENU_TEXT_SIZE
        )
        val menuItemTextColor =
            typedArray.getColorStateList(R.styleable.HiSliderView_menuItemTextColor)
                ?: generateColorStateList()

        val menuItemIndicator = typedArray.getDrawable(R.styleable.HiSliderView_menuItemIndicator)
            ?: context.getDrawable(R.drawable.shape_hi_slider_indicator)
//        val menuItemSelectTextColor = typedArray.getColor(R.styleable.HiSliderView_menuItemSelectTextColor,TEXT_COLOR_SELECT)
        val menuItemBackgroundColor =
            typedArray.getColor(R.styleable.HiSliderView_menuItemBackgroundColor, BG_COLOR_NORMAL)
        val menuItemSelectBackgroundColor = typedArray.getColor(
            R.styleable.HiSliderView_menuItemSelectBackgroundColor,
            BG_COLOR_SELECT
        )
        typedArray.recycle()
        return MenuItemAttr(
            menuItemWidth,
            menuItemHeight,
            menuItemTextColor,
            menuItemSelectBackgroundColor,
            menuItemBackgroundColor,
            menuItemTextSize,
            menuItemSelectTextSize,
            menuItemIndicator
        )
    }


    data class MenuItemAttr(
        val width: Int,
        val height: Int,
        val textColor: ColorStateList,
        val selectBackgroundColor: Int,
        val normalBackgroundColor: Int,
        val textSize: Int,
        val selectTextSize: Int,
        val indicator: Drawable?
    )

    private fun generateColorStateList(): ColorStateList {
        val states = Array(2) { IntArray(2) }
        val color = IntArray(2)
        states[0] = IntArray(1) { android.R.attr.state_selected }
        states[1] = IntArray(1)
        color[0] = TEXT_COLOR_SELECT
        color[1] = TEXT_COLOR_NORMAL
        return ColorStateList(states, color)
    }

    private fun applyUnit(unit: Int, value: Float): Int {
        return TypedValue.applyDimension(unit, value, context.resources.displayMetrics).toInt()
    }

}
