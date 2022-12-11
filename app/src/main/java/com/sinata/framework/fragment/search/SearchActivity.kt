package com.sinata.framework.fragment.search

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.RelativeLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import com.sinata.common.ui.view.EmptyView
import com.sinata.framework.R
import com.sinata.hi_library.utils.HiDisplayUtil
import com.sinata.hi_library.utils.HiRes
import com.sinata.hi_library.utils.HiStatusBar
import com.sinata.hi_ui.search.HiSearchView
import com.sinata.hi_ui.search.SimpleTextWatcher
import kotlinx.android.synthetic.main.activity_search.*

class SearchActivity : AppCompatActivity() {

    private lateinit var hiSearchView: HiSearchView
    private var quickSearchView: QuickSearchView?=null
    private var searchButton: Button? = null
    private var showView: View? = null
    private var emptyView: EmptyView? = null
    private val viewModel by viewModels<SearchViewModel>()
    private var status = -1

    companion object {
        const val STATUS_EMPTY = 0
        const val STATUS_HISTORY = 1
        const val STATUS_QUICK_SEARCH = 2
        const val STATUS_GOODS_SEARCH = 3
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        HiStatusBar.setStatusBar(this, true, translucent = true)
        setContentView(R.layout.activity_search)
//        viewModel = ViewModelProvider(this).get(SearchViewModel::class.java)
        initTopBar()
        updateViewStatus(STATUS_EMPTY)
        //query history list
    }

    private fun updateViewStatus(newStatus: Int) {
        if (status == newStatus) return
        status = newStatus
        when (status) {
            STATUS_EMPTY -> {
                if (emptyView == null) {
                    emptyView = EmptyView(this, null)
                    emptyView?.setDesc(HiRes.getString(R.string.list_empty_desc))
                    emptyView?.setIcon(R.string.if_empty)
                }

                showView = emptyView
            }

            STATUS_QUICK_SEARCH->{
                if (quickSearchView == null){
                    quickSearchView = QuickSearchView(this)
                }
                showView = quickSearchView
            }
        }

        if (showView != null) {
            if (showView!!.parent == null) {
                container.addView(showView)
            }

            val childCount = container.childCount
            for (index in 0 until childCount) {
                val childView = container.getChildAt(index)
                childView.visibility = if (childView == showView) View.VISIBLE else View.GONE

            }
        }
    }

    private fun initTopBar() {
        nav_bar.setNavListener(View.OnClickListener { onBackPressed() })
        searchButton = nav_bar.addRightTextButton(R.string.nav_item_search, R.id.id_nav_item_search)
        searchButton?.setTextColor(HiRes.getColorStateList(R.color.color_nav_item_search))
        searchButton?.setOnClickListener(searchClickListener)
        searchButton?.isEnabled = false
        hiSearchView = HiSearchView(this)
        hiSearchView.layoutParams = RelativeLayout.LayoutParams(
            RelativeLayout.LayoutParams.MATCH_PARENT,
            HiDisplayUtil.dp2Px(38f)
        )
        hiSearchView.setHintText(HiRes.getString(R.string.home_search_hint))
        hiSearchView.setClearIconClickListener(updateHistoryListener)
        hiSearchView.setDebounceTextChangedListener(debounceTextChangeListener)
        nav_bar.setCenterView(hiSearchView)

    }

    private val searchClickListener = View.OnClickListener {


    }

    private val updateHistoryListener = View.OnClickListener {

    }

    private val debounceTextChangeListener = object : SimpleTextWatcher() {
        override fun afterTextChanged(s: Editable?) {
            super.afterTextChanged(s)
            val hasContent = s != null && s.trim().isNotEmpty()
            searchButton?.isEnabled = hasContent
            if (hasContent) {
                viewModel?.querySearch(s.toString())
                    ?.observe(this@SearchActivity, Observer { keywords->
                        if (keywords.isNullOrEmpty()){
                            updateViewStatus(STATUS_EMPTY)
                        }else{
                            updateViewStatus(STATUS_QUICK_SEARCH)
                            quickSearchView?.bindData(keywords){ keyword ->
                                doKeywordSearch(keyword)
                            }
                        }
                    })
            }
        }
    }

    private fun doKeywordSearch(keyword: Keyword) {
        //1.搜索框搜索高亮
        hiSearchView.setKeyword(keyword.keyword,updateHistoryListener)
        //2.把keyword存储起来
        viewModel?.saveHistory(keyword)

        //3.发起goodssearch
        val kwClearIconView:View? = hiSearchView.findViewById(R.id.id_search_keyword_clear_icon)
        kwClearIconView?.isEnabled = false
        viewModel?.goodSearchLiveData
    }
}