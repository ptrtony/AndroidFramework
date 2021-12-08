package com.sinata.hi_ui.title

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.text.TextUtils
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.StringRes
import com.sinata.hi_library.log.utils.HiDisplayUtil
import com.sinata.hi_library.utils.HiRes
import com.sinata.hi_ui.R
import com.sinata.hi_ui.iconfont.IconFontButton
import com.sinata.hi_ui.iconfont.IconFontTextView

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司
@author jingqiang.cheng
@date 2021/10/13
 */
class HiNavigationBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    RelativeLayout(context, attrs, defStyleAttr) {
    //辅主标题
    private var titleView: IconFontTextView? = null
    private var subtitleView: IconFontTextView? = null
    private var titleContainer: LinearLayout? = null
    private val navAttrs: Attrs

    //左右按钮
    private val mLeftViewList = ArrayList<View>()
    private val mRightViewList = ArrayList<View>()
    private var mLeftLastViewId = View.NO_ID
    private var mRightLastViewId = View.NO_ID

    init {
        navAttrs = parseNavAttr(context, attrs, defStyleAttr)
        if (!TextUtils.isEmpty(navAttrs.navTitle)) {
            setTitle(navAttrs.navTitle!!)
        }

        if (!TextUtils.isEmpty(navAttrs.navSubTitle)) {
            setSubtitle(navAttrs.navSubTitle!!)
        }
    }

    fun setNavListener(listener: OnClickListener) {
        if (!TextUtils.isEmpty(navAttrs.navIcon)) {
            val navBackView = addLeftTextButton(navAttrs.navIcon!!, R.id.id_nav_left_back_view)
            navBackView.setOnClickListener(listener)
        }
    }

    fun addLeftTextButton(@StringRes stringRes: Int, viewId: Int): Button {
        return addLeftTextButton(HiRes.getString(stringRes), viewId)
    }

    fun addRightTextButton(@StringRes stringRes: Int, viewId: Int): Button {
        return addLeftTextButton(HiRes.getString(stringRes), viewId)
    }


    fun addLeftTextButton(buttonText: String, viewId: Int): Button {
        val button = generateTextButton()
        button.text = buttonText
        button.id = viewId
        if (mLeftViewList.isEmpty()) {
            button.setPadding(navAttrs.horPadding * 2, 0, navAttrs.horPadding, 0)
        } else {
            button.setPadding(navAttrs.horPadding, 0, navAttrs.horPadding, 0)
        }
        val params = generateTextButtonLayoutParams()
        params.addRule(CENTER_VERTICAL)
        addLeftView(button,params )
        return button
    }

    fun setCenterView(view: View) {
        var params = view.layoutParams
        if (params == null) {
            params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
        } else if (params !is LayoutParams) {
            params = LayoutParams(params)
        }
        val centerViewParams = params as LayoutParams
        centerViewParams.addRule(RIGHT_OF, mLeftLastViewId)
        centerViewParams.addRule(LEFT_OF, mRightLastViewId)
        centerViewParams.addRule(CENTER_VERTICAL)
        addView(view, params)
    }


    fun addRightTextButton(buttonText: String, viewId: Int): Button {
        val button = generateTextButton()
        button.text = buttonText
        button.id = viewId
        if (mRightViewList.isEmpty()) {
            button.setPadding(navAttrs.horPadding, 0, navAttrs.horPadding * 2, 0)
        } else {
            button.setPadding(navAttrs.horPadding, 0, navAttrs.horPadding, 0)
        }
        val params = generateTextButtonLayoutParams()
        params.addRule(CENTER_VERTICAL)
        addRightView(button, params)
        return button
    }

    private fun addLeftView(
        view: View,
        params: LayoutParams
    ) {
        val viewId = view.id
        if (viewId == View.NO_ID) {
            throw IllegalStateException("left view must has an unique id.")
        }
        if (mLeftLastViewId == View.NO_ID) {
            params.addRule(ALIGN_PARENT_LEFT, viewId)
        } else {
            params.addRule(RIGHT_OF, mLeftLastViewId)
        }
        mLeftLastViewId = viewId
        params.alignWithParent = true //alignParentIfMissing
        mLeftViewList.add(view)
        addView(view, params)
    }


    private fun addRightView(
        view: View,
        params: LayoutParams
    ) {
        val viewId = view.id
        if (viewId == View.NO_ID) {
            throw IllegalStateException("right view must has an unique id.")
        }
        if (mRightLastViewId == View.NO_ID) {
            params.addRule(ALIGN_PARENT_RIGHT, viewId)
        } else {
            params.addRule(LEFT_OF, mRightLastViewId)
        }
        mRightLastViewId = viewId
        params.alignWithParent = true //alignParentIfMissing
        mRightViewList.add(view)
        addView(view, params)
    }


    fun setTitle(title: String) {
        ensureTitleView()
        titleView?.text = title
        updateTitleViewStyle()
        titleView?.visibility = if (TextUtils.isEmpty(title)) View.GONE else View.VISIBLE
    }

    fun setSubtitle(subtitle: String) {
        ensureSubtitleView()
        subtitleView?.text = subtitle
        subtitleView?.visibility = if (TextUtils.isEmpty(subtitle)) View.GONE else View.VISIBLE
    }

    private fun ensureTitleView() {
        if (titleView == null) {
            titleView = IconFontTextView(context)
            titleView?.apply {
                gravity = Gravity.CENTER
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(navAttrs.titleTextColor)
                updateTitleViewStyle()
                titleContainer?.addView(titleView, 0)
            }
        }

    }


    private fun ensureSubtitleView() {
        if (subtitleView == null) {
            subtitleView = IconFontTextView(context)
            subtitleView?.apply {
                gravity = Gravity.CENTER
                isSingleLine = true
                ellipsize = TextUtils.TruncateAt.END
                setTextColor(navAttrs.subTitleTextColor)
                setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttrs.subTitleTextSize)

                //添加到titleContainer
                ensureTitleContainer()
                titleContainer?.addView(subtitleView)
            }
        }
    }

    private fun ensureTitleContainer() {
        if (titleContainer == null) {
            titleContainer = LinearLayout(context)
            titleContainer?.apply {
                orientation = LinearLayout.VERTICAL
                gravity = Gravity.CENTER
                val params = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT)
                params.addRule(CENTER_IN_PARENT)
                this@HiNavigationBar.addView(titleContainer, params)
            }
        }
    }

    private fun updateTitleViewStyle() {
        if (titleView != null) {
            if (subtitleView == null || TextUtils.isEmpty(subtitleView!!.text)) {
                titleView?.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttrs.titleTextSize)
                titleView?.typeface = Typeface.DEFAULT_BOLD
            } else {
                titleView?.setTextSize(
                    TypedValue.COMPLEX_UNIT_PX,
                    navAttrs.titleTextSizeWithSubtitle
                )
                titleView?.typeface = Typeface.DEFAULT
            }
        }
    }


    private fun generateTextButtonLayoutParams(): LayoutParams {
        return LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
    }


    private fun generateTextButton(): Button {
        val button = IconFontButton(context)
        button.setBackgroundColor(0)
        button.minWidth = 0
        button.minimumWidth = 0
        button.minHeight = 0
        button.minimumHeight = 0
        button.setTextSize(TypedValue.COMPLEX_UNIT_PX, navAttrs.btnTextSize)
        button.setTextColor(navAttrs.btnTextColor)
        return button
    }

    private fun parseNavAttr(context: Context, attrs: AttributeSet?, defStyleAttr: Int): Attrs {
        val value = TypedValue()
        context.theme.resolveAttribute(R.attr.navigationStyle, value, true)
        val defStyleRes =
            if (value.resourceId != 0) value.resourceId else R.style.navigationStyle
        val array = context.obtainStyledAttributes(
            attrs,
            R.styleable.HiNavigationBar,
            defStyleAttr,
            defStyleRes
        )
        val navIcon = array.getString(R.styleable.HiNavigationBar_nav_icon)
        val navTitle = array.getString(R.styleable.HiNavigationBar_nav_title)
        val navSubTitle = array.getString(R.styleable.HiNavigationBar_nav_subtitle)
        val horPadding = array.getDimensionPixelSize(R.styleable.HiNavigationBar_hor_padding, 0)
        val btnTextSize = array.getDimensionPixelSize(
            R.styleable.HiNavigationBar_text_btn_text_size,
            HiDisplayUtil.sp2px(16f)
        )
        val btnTextColor =
            array.getColorStateList(R.styleable.HiNavigationBar_text_btn_text_color)
        val titleTextSizeWithSubtitle = array.getDimensionPixelSize(
            R.styleable.HiNavigationBar_title_text_size_with_subTitle,
            HiDisplayUtil.sp2px(16f)
        )
        val titleTextSize = array.getDimensionPixelSize(
            R.styleable.HiNavigationBar_title_text_size,
            HiDisplayUtil.sp2px(17f)
        )
        val titleTextColor = array.getColor(
            R.styleable.HiNavigationBar_title_text_color,
            HiRes.getColor(R.color.hi_tabtop_normal_color)
        )
        val subTitleTextSize = array.getDimensionPixelSize(
            R.styleable.HiNavigationBar_subTitle_text_size,
            HiDisplayUtil.sp2px(14f)
        )
        val subTitleTextColor = array.getColor(
            R.styleable.HiNavigationBar_subTitle_text_color,
            HiRes.getColor(R.color.hi_tabtop_normal_color)
        )
        array.recycle()

        return Attrs(
            navIcon,
            navTitle,
            navSubTitle,
            horPadding,
            btnTextSize.toFloat(),
            btnTextColor,
            titleTextSizeWithSubtitle.toFloat(),
            titleTextSize.toFloat(),
            titleTextColor,
            subTitleTextSize.toFloat(),
            subTitleTextColor
        )
    }

    private data class Attrs(
        val navIcon: String?,
        val navTitle: String?,
        val navSubTitle: String?,
        val horPadding: Int,
        val btnTextSize: Float,
        val btnTextColor: ColorStateList?,
        val titleTextSizeWithSubtitle: Float,
        val titleTextSize: Float,
        val titleTextColor: Int,
        val subTitleTextSize: Float,
        val subTitleTextColor: Int
    )


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (titleContainer != null) {
            //计算出标题左侧使用了多少空间
            var leftUseSpace = paddingLeft
            for (view in mLeftViewList) {
                leftUseSpace += view.measuredWidth
            }

            var rightUseSpace = paddingRight
            for (view in mRightViewList) {
                rightUseSpace += view.measuredWidth
            }

            val titleContainerWidth = titleContainer!!.measuredWidth
            val remainingSpace = measuredWidth - leftUseSpace.coerceAtLeast(rightUseSpace) * 2
            if (remainingSpace < titleContainerWidth) {
                val size = MeasureSpec.makeMeasureSpec(remainingSpace, MeasureSpec.EXACTLY)
                titleContainer!!.measure(size, heightMeasureSpec)
            }
        }
    }


}