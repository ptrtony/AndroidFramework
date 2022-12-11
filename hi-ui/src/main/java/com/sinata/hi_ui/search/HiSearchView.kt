package com.sinata.hi_ui.search

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputFilter
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import com.sinata.hi_library.utils.HiDisplayUtil
import com.sinata.hi_library.utils.MainHandler
import com.sinata.hi_ui.R
import com.sinata.hi_ui.iconfont.IconFontTextView
import java.lang.IllegalStateException

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:


@author jingqiang.cheng
@date 2021/10/14
 */

class HiSearchView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr) {

    companion object {
        const val LEFT = 1
        const val CENTER = 0
        const val DEBOUNCE_TRIGGER_DURATION = 200L
    }

    private var simpleTextWatcher: SimpleTextWatcher? = null
    private var editText: EditText? = null

    //搜索小图标 和 默认的提示语，以及container
    private var searchIcon: IconFontTextView? = null
    private var hintTv: TextView? = null
    private var searchIconHintContainer: LinearLayout? = null

    //右侧清除小图标
    private var clearIcon: IconFontTextView? = null

    //keyword
    private var keywordContainer: LinearLayout? = null
    private var keywordTv: TextView? = null
    private var kwClearIcon: IconFontTextView? = null
    private val viewAttrs: AttrsParse.Attrs =
        AttrsParse.parseSearchViewAttrs(context, attrs, defStyleAttr)

    init {
        //初始化editText
        initEditText()
        //初始化右侧一键清除的小按钮
        initClearIcon()
        //初始化 默认的提示语和searchIcon
        initSearchIconHintContainer()

        background = viewAttrs.searchBackground
        editText?.addTextChangedListener(object : SimpleTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                super.afterTextChanged(s)
                val hasContent = s?.length ?: 0 > 0
                changeVisibility(clearIcon, hasContent)
                changeVisibility(searchIconHintContainer, !hasContent)

                if (simpleTextWatcher != null) {
                    MainHandler.remove(debounceRunnable)
                    MainHandler.postDelayed(debounceRunnable, DEBOUNCE_TRIGGER_DURATION)
                }
            }
        })
    }

    private val debounceRunnable = Runnable {
        if (simpleTextWatcher != null) {
            simpleTextWatcher!!.afterTextChanged(editText?.text)
        }
    }

    fun setDebounceTextChangedListener(simpleTextWatcher: SimpleTextWatcher) {
        this.simpleTextWatcher = simpleTextWatcher;
    }

    private fun changeVisibility(view: View?, hasContent: Boolean) {
        view?.visibility = if (hasContent) VISIBLE else GONE
    }

    private fun initSearchIconHintContainer() {
        //hint view -- start
        hintTv = TextView(context)
        hintTv?.setTextColor(viewAttrs.hintTextColor)
        hintTv?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.hintTextSize)
        hintTv?.text = viewAttrs.hintText
        hintTv?.isSingleLine = true
        hintTv?.id = R.id.id_search_hint_view
        //hint view --end

        //search icon --start
        searchIcon = IconFontTextView(context)
        searchIcon?.setTextColor(viewAttrs.searchTextColor)
        searchIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.searchTextSize)
        searchIcon?.setTextColor(viewAttrs.hintTextColor)
        searchIcon?.text = viewAttrs.searchIcon
        searchIcon?.id = R.id.id_search_icon
        searchIcon?.setPadding(0, 0, viewAttrs.iconPadding / 2, 0)
        //search icon --end

        //icon hint container--start
        searchIconHintContainer = LinearLayout(context)
        searchIconHintContainer?.orientation = LinearLayout.HORIZONTAL
        searchIconHintContainer?.gravity = Gravity.CENTER
        searchIconHintContainer?.addView(searchIcon)
        searchIconHintContainer?.addView(hintTv)
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_VERTICAL)
        when (viewAttrs.gravity) {
            LEFT -> params.addRule(ALIGN_PARENT_LEFT)
            CENTER -> params.addRule(CENTER_IN_PARENT)
            else -> throw IllegalStateException("not support gravity for now")
        }
        addView(searchIconHintContainer, params)
        //icon hint container--end

    }

    private fun initClearIcon() {
        if (TextUtils.isEmpty(viewAttrs.clearIcon)) return
        clearIcon = IconFontTextView(context)
        clearIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.clearIconSize)
        clearIcon?.text = viewAttrs.clearIcon
        clearIcon?.setTextColor(viewAttrs.searchTextColor)
        clearIcon?.setPadding(
            viewAttrs.iconPadding,
            viewAttrs.iconPadding,
            viewAttrs.iconPadding,
            viewAttrs.iconPadding
        )
        clearIcon?.id = R.id.id_search_clear_view
        clearIcon?.visibility = View.GONE
        val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        params.addRule(CENTER_VERTICAL)
        params.addRule(ALIGN_PARENT_RIGHT)
        addView(clearIcon, params)
    }

    private fun initEditText() {
        editText = EditText(context)
        editText?.setTextColor(viewAttrs.searchTextColor)
        editText?.setBackgroundColor(Color.TRANSPARENT)
        editText?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.searchTextSize)
        //防止文字输入过于贴近输入的两边
        editText?.setPadding(viewAttrs.iconPadding, 0, viewAttrs.iconPadding, 0)
        editText?.id = R.id.id_search_edit_view
        val params = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        params.addRule(CENTER_VERTICAL)
        addView(editText, params)
    }

    fun setKeyword(keyword: String?, listener: OnClickListener) {
        //当用户点击 联想词面板的时候，会调用该方法，把关键字设置到搜索框上
        ensureKeywordContainer()
        toggleSearchViewVisibility(true)
        editText?.text = null
        keywordTv?.text = keyword
        keywordTv?.setOnClickListener {
            listener.onClick(it)
            toggleSearchViewVisibility(false)
        }
    }

    fun setClearIconClickListener(listener: OnClickListener) {
        clearIcon?.setOnClickListener {
            editText?.text = null
            clearIcon?.visibility = View.GONE
            searchIcon?.visibility = View.VISIBLE
            hintTv?.visibility = View.VISIBLE
            searchIconHintContainer?.visibility = View.VISIBLE
        }
    }

    fun setHintText(hintText: String) {
        hintTv?.text = hintText
    }

    private fun toggleSearchViewVisibility(showKeyword: Boolean) {
        editText?.visibility = if (showKeyword) View.GONE else View.VISIBLE
        clearIcon?.visibility = View.GONE
        searchIconHintContainer?.visibility = if (showKeyword) View.GONE else View.VISIBLE
        searchIcon?.visibility = if (showKeyword) View.GONE else View.VISIBLE
        hintTv?.visibility = if (showKeyword) View.GONE else View.VISIBLE
        keywordContainer?.visibility = if (showKeyword) View.VISIBLE else View.GONE


    }

    private fun ensureKeywordContainer() {
        if (keywordContainer != null) return
        if (!TextUtils.isEmpty(viewAttrs.keywordClearIcon)) {
            kwClearIcon = IconFontTextView(context)
            kwClearIcon?.setTextColor(viewAttrs.keywordColor)
            kwClearIcon?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.keywordClearIconSize)
            kwClearIcon?.text = viewAttrs.keywordClearIcon
            kwClearIcon?.id = R.id.id_search_keyword_clear_icon
            kwClearIcon?.setPadding(
                viewAttrs.iconPadding,
                viewAttrs.iconPadding / 2,
                viewAttrs.iconPadding,
                viewAttrs.iconPadding / 2
            )
        }

        keywordTv = TextView(context)
        keywordTv?.setTextColor(viewAttrs.keywordColor)
        keywordTv?.setTextSize(TypedValue.COMPLEX_UNIT_PX, viewAttrs.keywordSize)
        keywordTv?.includeFontPadding = false
        keywordTv?.isSingleLine = true
        keywordTv?.gravity = Gravity.CENTER_VERTICAL
        keywordTv?.ellipsize = TextUtils.TruncateAt.END
        keywordTv?.filters = arrayOf(InputFilter.LengthFilter(viewAttrs.keywordMaxLen))
        keywordTv?.id = R.id.id_search_keyword_text_view
        keywordTv?.setPadding(
            viewAttrs.iconPadding,
            0,
            0,
            0
        )



        keywordContainer = LinearLayout(context)
        keywordContainer?.orientation = LinearLayout.HORIZONTAL
        keywordContainer?.gravity = Gravity.CENTER
        keywordContainer?.background = viewAttrs.keywordBackground
        keywordContainer?.addView(
            keywordTv, LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                HiDisplayUtil.dp2Px(30f)
            )
        )

        if (kwClearIcon != null) {
            keywordContainer?.addView(
                kwClearIcon, LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    HiDisplayUtil.dp2Px(30f)
                )
            )
        }

        val kwParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        kwParams.addRule(CENTER_VERTICAL)
        kwParams.addRule(ALIGN_PARENT_LEFT)
        kwParams.leftMargin = viewAttrs.iconPadding
        kwParams.rightMargin = viewAttrs.iconPadding
        addView(keywordContainer, kwParams)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        MainHandler.remove(debounceRunnable)
    }
}