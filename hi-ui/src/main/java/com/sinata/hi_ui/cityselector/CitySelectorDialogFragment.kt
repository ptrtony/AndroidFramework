package com.sinata.hi_ui.cityselector

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.TextUtils
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
import android.widget.CheckedTextView
import androidx.appcompat.app.AppCompatDialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import com.sinata.hi_library.utils.HiDisplayUtil
import com.sinata.hi_library.utils.HiRes
import com.sinata.hi_ui.R
import com.sinata.hi_ui.recyclerview.HiViewHolder
import com.sinata.hi_ui.top.HiTabTopInfo
import kotlinx.android.synthetic.main.dialog_city_selector.*

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/27
 */

class CitySelectorDialogFragment : AppCompatDialogFragment() {

    private var citySelectListener: OnCitySelectListener? = null
    private lateinit var province: Province
    private var dataSets: List<Province>? = null
    private val defaultColor = HiRes.getColor(R.color.color_333)
    private val selectColor = HiRes.getColor(R.color.color_dd2)
    private val pleasePickStr = HiRes.getString(R.string.city_selector_tab_hint)

    //tabLayout 的选中项
    private var topTabSelectIndex: Int = 0

    companion object {
        private const val KEY_PARAMS_DATA_SET = "key_data_set"
        private const val KEY_PARAMS_DATA_SELECT = "key_data_select"
        private const val TAB_PROVINCE = 0
        private const val TAB_CITY = 1
        private const val TAB_DISTRICT = 2
        fun newInstance(province: Province?, list: List<Province>): CitySelectorDialogFragment {
            val args = Bundle()
            val fragment = CitySelectorDialogFragment()
            args.putParcelable(KEY_PARAMS_DATA_SET, province)
            args.putParcelableArrayList(KEY_PARAMS_DATA_SELECT, ArrayList(list))
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val window = dialog?.window
        val contentView = inflater.inflate(
            R.layout.dialog_city_selector,
            window?.findViewById(android.R.id.content) ?: container,
            false
        )
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            (HiDisplayUtil.getDisplayHeightInPx(contentView?.context!!) * 0.6).toInt()
        )
        window?.setGravity(Gravity.BOTTOM)
        return contentView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        close.setOnClickListener {
            dismiss()
        }

        this.province = arguments?.getParcelable(KEY_PARAMS_DATA_SELECT) ?: Province()
        this.dataSets = arguments?.getParcelableArrayList(KEY_PARAMS_DATA_SET)
        requireNotNull(dataSets) { "params dataSets cannot be null" }
        //更新tabLayout的标签数量
        refreshTabLayoutCount()
        tab_layout.addTabSelectedChangeListener { index, preInfo, nextInfo ->
            if (viewpager.currentItem != index) {
                viewpager.setCurrentItem(index, false)
            }
        }
        viewpager.addOnPageChangeListener(object : ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                if (position != topTabSelectIndex) {
                    tab_layout.defaultSelected(topTabs[position])
                    topTabSelectIndex = position
                }
            }
        })

        viewpager.adapter = CityPagerAdapter { tabIndex, selectDistrict ->
            //tabIndex代表就是哪一个列表发生了点击事件
            //selectDistrict代表该列表选中的数据对象(省市区)
            when (selectDistrict.type) {
                TYPE_PROVINCE -> {
                    province = selectDistrict as Province
                }

                TYPE_CITY -> {
                    province.selectCity = selectDistrict as City
                }

                TYPE_DISTRICT -> {
                    province.selectDistrict = selectDistrict
                }
            }

            if (!TextUtils.equals(selectDistrict.type, TYPE_DISTRICT)) {
                refreshTabLayoutCount()
            } else {
                citySelectListener?.onCitySelect(province)
                dismiss()
            }
        }
    }

    //根据province 更新tabLayout的标签的数据
    //province ------> 拉起选择器的时候 传递过来的
    //province ------> 本次拉起选择器的每一次选择，都会记录到province
    //每一次选择都会调用该方法，更新tabLayout的个数
    private val topTabs = mutableListOf<HiTabTopInfo<Int>>()
    private fun refreshTabLayoutCount() {
        topTabs.clear()
        var addPleasePickTab = true

        //构建省tab
        if (!TextUtils.isEmpty(province.id)) {
            topTabs.add(newTabTopInfo(province.districtName))
        }
        //构建市tab
        if (province.selectCity != null) {
            topTabs.add(newTabTopInfo(province.selectCity!!.districtName))
        }
        //构建区tab
        if (province.selectDistrict != null) {
            topTabs.add(newTabTopInfo(province.selectDistrict!!.districtName))
            addPleasePickTab = false
        }

        if (addPleasePickTab) {
            topTabs.add(newTabTopInfo(pleasePickStr))
        }
        viewpager?.adapter?.notifyDataSetChanged()
        tab_layout.post {
            tab_layout.inflateInfo(topTabs as List<HiTabTopInfo<*>>)
            tab_layout.defaultSelected(topTabs[if (addPleasePickTab) topTabs.size - 1 else 0])
        }
    }

    private fun newTabTopInfo(districtName: String?): HiTabTopInfo<Int> =
        HiTabTopInfo(districtName, defaultColor, selectColor)


    inner class CityPagerAdapter(private val itemClickCallback: (Int, District) -> Unit) :
        PagerAdapter() {

        private var views = SparseArray<CityListView>(3)
        override fun instantiateItem(container: ViewGroup, position: Int): Any {
            //1.需要完成对应页面的view创建
            val view = views.get(position) ?: CityListView(context!!)
            views.put(position, view)
            //2.给这个view设置数据
            //3.找出每一个页面position = 0 (省份) 他的选中项
            val select: District?
            val list = when (position) {
                TAB_PROVINCE -> {
                    select = province //如果还未选择过，自然是没有，如果是选择过了，province是不为null的，就是当前页 选中项了
                    dataSets
                }
                TAB_CITY -> {
                    select = province.selectCity
                    province.selectCity
                }

                TAB_DISTRICT -> {
                    select = province.selectDistrict
                    province.selectDistrict
                }
                else -> throw IllegalStateException("pageCount must be less then ${dataSets?.size}")
            }
            view.setData(select, list as ArrayList<District>) { selectDistrict ->
                if (viewpager.currentItem != position) return@setData
                itemClickCallback(position, selectDistrict)
            }
            if (view.parent != null) container.addView(view)
            return view

        }

        override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
            super.destroyItem(container, position, `object`)
            container.removeView(views[position])
        }

        override fun getCount(): Int {
            return topTabs.size
        }

        override fun getItemPosition(`object`: Any): Int {
            //需要根据object ，其实就是instantiation返回的值，也就是CityListView
            //来判断他是第几个页面
            return if (views.indexOfValue(`object` as CityListView?) > 0) POSITION_NONE else POSITION_UNCHANGED
        }

        override fun isViewFromObject(view: View, `object`: Any): Boolean {
            return view == `object`
        }

    }


    inner class CityListView @JvmOverloads constructor(
        context: Context,
        attrs: AttributeSet? = null,
        defStyleAttr: Int = 0
    ) :
        RecyclerView(context, attrs, defStyleAttr) {

        private lateinit var onItemClick: (District) -> Unit
        private var lastSelectDistrict: District? = null
        private var districtList = ArrayList<District>()
        private var lastSelectIndex = -1
        private var currentSelectIndex = -1
        fun setData(select: District?, list: List<District>?, onItemClick: (District) -> Unit) {
            if (list.isNullOrEmpty()) return
            this.onItemClick = onItemClick
            lastSelectDistrict = select
            districtList.clear()
            districtList.addAll(list)
            post {
                adapter?.notifyDataSetChanged()
            }
        }

        init {
            layoutManager = LinearLayoutManager(context, VERTICAL, false)
            adapter = object : RecyclerView.Adapter<HiViewHolder>() {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): HiViewHolder {
                    return HiViewHolder(
                        LayoutInflater.from(context)
                            .inflate(R.layout.dialog_city_selector_list_item, parent, false)
                    )
                }

                override fun onBindViewHolder(holder: HiViewHolder, position: Int) {
                    val checkedTextView = holder.findViewById<CheckedTextView>(R.id.title)
                    val district = districtList[position]
                    checkedTextView.text = district.districtName

                    holder.itemView.setOnClickListener {
                        lastSelectDistrict = district
                        if (lastSelectIndex != -1)
                            notifyItemChanged(lastSelectIndex)
                        notifyItemChanged(position)
                        lastSelectIndex = position

                    }

                    //点击之后触发刷新，说明当前item是本次选中项
                    if (currentSelectIndex == position && currentSelectIndex != lastSelectIndex) {
                        onItemClick(district)
                    }

                    //首次进入或者点击之后的刷新item状态的正确性
                    if (lastSelectDistrict?.id == district.id) {
                        currentSelectIndex = position
                        lastSelectIndex = position
                    }

                    //改变item的状态
                    checkedTextView.isChecked = currentSelectIndex == position
                }

                override fun getItemCount(): Int {
                    return districtList.size
                }

            }
        }
    }

    fun setOnCitySelectListener(listener: OnCitySelectListener) {
        this.citySelectListener = listener
    }

    interface OnCitySelectListener {
        fun onCitySelect(province: Province)
    }
}