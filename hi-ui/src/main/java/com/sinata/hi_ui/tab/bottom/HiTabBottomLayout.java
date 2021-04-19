package com.sinata.hi_ui.tab.bottom;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ScrollView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.RecyclerView;

import com.sinata.hi_library.log.utils.HiDisplayUtil;
import com.sinata.hi_library.log.utils.HiViewUtil;
import com.sinata.hi_ui.R;
import com.sinata.hi_ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 11:56 PM
 * @Describe:
 */
public class HiTabBottomLayout extends FrameLayout implements IHiTabLayout<HiTabBottom, HiTabBottomInfo<?>> {
    private List<OnTabSelectedListener<HiTabBottomInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabBottomInfo<?> selectInfo;
    private float bottomAlpha = 1f;
    private float tabBottomAlpha = 1f;
    //TabBottom的高度
    private float tabBottomHeight = 50;
    //TabBottom头部线条高度
    private float bottomLineHeight = 0.5f;
    //TabBottom的头部线条颜色
    private String bottomLineColor = "#dfe0e1";
    private List<HiTabBottomInfo<?>> infoList;


    public HiTabBottomLayout(@NonNull Context context) {
        super(context);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTabBottomLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public HiTabBottom findTab(@NonNull HiTabBottomInfo<?> info) {
        ViewGroup ll = findViewWithTag(TAG_TAB_BOTTOM);
        for (int i = 0; i < getChildCount(); i++) {
            View child = ll.getChildAt(i);
            if (child instanceof HiTabBottom){
                HiTabBottom tab = (HiTabBottom) child;
                if (tab.getTabInfo() == info){
                    return tab;
                }
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabBottomInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabBottomInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }


    private static final String TAG_TAB_BOTTOM = "TAG_TAB_BOTTOM";

    @Override
    public void inflateInfo(@Nullable List<HiTabBottomInfo<?>> infoList) {
        if (infoList == null) {
            return;
        }
        this.infoList = infoList;
        //移除之前已经添加的view
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectInfo = null;
        addBackground();
        //清除之前添加的HiTabBottom listener Tips:Java foreach remove问题
        Iterator<OnTabSelectedListener<HiTabBottomInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabBottom) {
                iterator.remove();
            }
        }
        FrameLayout ll = new FrameLayout(getContext());
        ll.setTag(TAG_TAB_BOTTOM);
        int height = HiDisplayUtil.dp2px(getContext(), tabBottomHeight);
        int width = HiDisplayUtil.getScreenWidth((Activity) getContext()) / infoList.size();
        for (int i = 0; i < infoList.size(); i++) {
            final HiTabBottomInfo<?> hiTabBottomInfo = infoList.get(i);
            //Tips:为何不用LinearLayout:当动态改变child大小后Gravity会失效
            LayoutParams params = new LayoutParams(width, height);
            params.leftMargin = i * width;
            params.gravity = Gravity.BOTTOM;
            HiTabBottom hiTabBottom = new HiTabBottom(getContext());
            tabSelectedListeners.add(hiTabBottom);
            hiTabBottom.setHiTabInfo(hiTabBottomInfo);
            ll.addView(hiTabBottom, params);
            hiTabBottom.setOnClickListener(v -> onSelected(hiTabBottomInfo));
        }
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addBottomLine();
        addView(ll, params);
        fixContentView();
    }

    private void addBottomLine() {
        View lineView = new View(getContext());
        int lineHeight = HiDisplayUtil.dp2px(getContext(), bottomLineHeight);
        LayoutParams lineParams = new LayoutParams(LayoutParams.MATCH_PARENT, lineHeight);
        lineParams.bottomMargin = HiDisplayUtil.dp2px(getContext(), tabBottomHeight - bottomLineHeight);
        lineParams.gravity = Gravity.BOTTOM;
        addView(lineView, lineParams);
        lineView.setAlpha(bottomAlpha);
    }

    private void onSelected(HiTabBottomInfo<?> nextInfo) {
        for (OnTabSelectedListener<HiTabBottomInfo<?>> listener : tabSelectedListeners) {
            listener.onTabSelectedChange(infoList.indexOf(nextInfo), selectInfo, nextInfo);
        }
        this.selectInfo = nextInfo;
    }

    private void addBackground() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.hi_bottom_layout_bg, null);
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, HiDisplayUtil.dp2px(getContext(), tabBottomHeight));
        params.gravity = Gravity.BOTTOM;
        addView(view, params);
        view.setAlpha(tabBottomAlpha);
    }

    /**
     * 修复内容区域的底部的padding
     */
    private void fixContentView(){
        if (!(getChildAt(0) instanceof ViewGroup)){
            return;
        }
        ViewGroup rootView = (ViewGroup) getChildAt(0);
        ViewGroup targetView = HiViewUtil.findTypeView(rootView, RecyclerView.class);
        if (targetView == null){
            targetView = HiViewUtil.findTypeView(rootView, AbsListView.class);
        }
        if (targetView == null){
            targetView = HiViewUtil.findTypeView(rootView, ScrollView.class);
        }

        if (targetView == null){
            targetView = HiViewUtil.findTypeView(rootView, NestedScrollView.class);
        }

        if (targetView != null){
            targetView.setPadding(0,0,0,HiDisplayUtil.dp2px(getContext(),tabBottomHeight));
        }
    }


    public float getBottomAlpha() {
        return bottomAlpha;
    }

    public void setBottomAlpha(float bottomAlpha) {
        this.bottomAlpha = bottomAlpha;
    }

    public float getTabBottomAlpha(){
        return tabBottomAlpha;
    }

    public void setTabBottomAlpha(float tabBottomAlpha){
        this.tabBottomAlpha = tabBottomAlpha;
    }

    public float getTabHeight() {
        return tabBottomHeight;
    }

    public void setTabBottomHeight(float tabHeight) {
        this.tabBottomHeight = tabHeight;
    }

    public float getBottomLineHeight() {
        return bottomLineHeight;
    }

    public void setBottomLineHeight(float bottomLineHeight) {
        this.bottomLineHeight = bottomLineHeight;
    }

    public String getBottomLineColor() {
        return bottomLineColor;
    }

    public void setBottomLineColor(String bottomLineColor) {
        this.bottomLineColor = bottomLineColor;
    }

}
