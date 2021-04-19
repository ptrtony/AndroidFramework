package com.sinata.hi_ui.top;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.sinata.hi_library.log.utils.HiDisplayUtil;
import com.sinata.hi_ui.tab.common.IHiTabLayout;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author cjq
 * @Date 16/4/2021
 * @Time 10:10 PM
 * @Describe:
 */
public class HiTabTopLayout extends HorizontalScrollView implements IHiTabLayout<HiTabTop, HiTabTopInfo<?>> {

    private List<OnTabSelectedListener<HiTabTopInfo<?>>> tabSelectedListeners = new ArrayList<>();
    private HiTabTopInfo<?> selectedInfo;
    private List<HiTabTopInfo<?>> infoList;

    public HiTabTopLayout(Context context) {
        this(context, null);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTopLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setHorizontalScrollBarEnabled(false);
    }

    @Override
    public HiTabTop findTab(@NonNull HiTabTopInfo<?> info) {
        ViewGroup ll = getRootLayout(false);
        for (int i = 0, childCount = ll.getChildCount(); i < childCount; i++) {
            HiTabTop tab = (HiTabTop) ll.getChildAt(i);
            if (tab.getTabInfo() == info) {
                return tab;
            }
        }
        return null;
    }

    @Override
    public void addTabSelectedChangeListener(OnTabSelectedListener<HiTabTopInfo<?>> listener) {
        tabSelectedListeners.add(listener);
    }

    @Override
    public void defaultSelected(@NonNull HiTabTopInfo<?> defaultInfo) {
        onSelected(defaultInfo);
    }

    @Override
    public void inflateInfo(@NonNull List<HiTabTopInfo<?>> infoList) {
        if (infoList == null || infoList.isEmpty()) {
            return;
        }
        this.infoList = infoList;
        LinearLayout linearLayout = getRootLayout(true);
        //移除之前已经添加的view
        for (int i = getChildCount() - 1; i > 0; i--) {
            removeViewAt(i);
        }
        selectedInfo = null;
        //清除之前添加的HiTabBottom listener Tips:Java foreach remove问题
        Iterator<OnTabSelectedListener<HiTabTopInfo<?>>> iterator = tabSelectedListeners.iterator();
        while (iterator.hasNext()) {
            if (iterator.next() instanceof HiTabTop) {
                iterator.remove();
            }
        }
        for (int i = 0; i < infoList.size(); i++) {
            HiTabTopInfo<?> tabInfo = infoList.get(i);
            HiTabTop tab = new HiTabTop(getContext());
            tabSelectedListeners.add(tab);
            tab.setHiTabInfo(tabInfo);
            linearLayout.addView(tab);
            tab.setOnClickListener(v -> onSelected(tabInfo));
        }
    }

    private void onSelected(HiTabTopInfo<?> tabInfo) {
        for (int i = 0; i < tabSelectedListeners.size(); i++) {
            OnTabSelectedListener<HiTabTopInfo<?>> hiTabTopInfoOnTabSelectedListener = tabSelectedListeners.get(i);
            hiTabTopInfoOnTabSelectedListener.onTabSelectedChange(infoList.indexOf(tabInfo), selectedInfo, tabInfo);
        }
        selectedInfo = tabInfo;
        autoScroll(tabInfo);
    }

    /**
     * 获取可以滑动的范围
     * @param tabInfo
     */
    private void autoScroll(HiTabTopInfo<?> tabInfo) {
        HiTabTop hiTabTop = findTab(tabInfo);
        if (hiTabTop == null) return;
        int index = infoList.indexOf(tabInfo);
        int[] loc = new int[2];
        hiTabTop.getLocationInWindow(loc);
        int scrollWidth;
        if (tabWidth == 0) {
            tabWidth = hiTabTop.getWidth();
        }

        if (loc[0] + tabWidth / 2 > HiDisplayUtil.getDisplayWidthInPx(getContext()) / 2) {
            scrollWidth = rangeScrollWidth(index,2);
        }else {
            scrollWidth = rangeScrollWidth(index,-2);
        }
        scrollTo(getScrollX() + scrollWidth,0);
    }

    /**
     *
     * @param index 从第几个开始
     * @param range 向前向后的范围
     * @return  可滚动的范围
     */
    private int rangeScrollWidth(int index,int range){
        int scrollWidth = 0;
        for (int i = 0; i < Math.abs(range); i++) {
            int next;
            if (range < 0){//向左滑动的next
                next = range + i + index;
            }else{//向右滑动的next
                next = range - i + index;
            }
            if (next >=0 && next < infoList.size()){
                if (range < 0){
                    scrollWidth -= scrollWidth(next,false);
                }else{
                    scrollWidth += scrollWidth(next,true);
                }
            }
        }
        return scrollWidth;
    }

    /**
     * 指定位置的控件可滚动的距离
     * @param index 指定位置的控件
     * @param toRight 是否是点击了屏幕的右测
     * @return 可滚动的距离
     */
    private int scrollWidth(int index,boolean toRight){
        HiTabTop target = findTab(infoList.get(index));
        if (target == null)return 0;
        Rect rect = new Rect();
        target.getLocalVisibleRect(rect);
        if (toRight){//点击屏幕右侧
            if (rect.right > tabWidth){//right坐标大于控件的宽度时，说明没有完全展示
                return tabWidth;
            }else{
                return tabWidth - rect.right;
            }
        }else{
            if (rect.left <= -tabWidth){//left坐标小于-控件的宽度时，说明没有完全的展示
                return tabWidth;
            }else{
                return rect.left;
            }
        }
    }
    private int tabWidth;

    private LinearLayout getRootLayout(boolean clear) {
        LinearLayout rootView = (LinearLayout) getChildAt(0);
        if (rootView == null) {
            rootView = new LinearLayout(getContext());
            rootView.setOrientation(LinearLayout.HORIZONTAL);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            addView(rootView, layoutParams);
        } else if (clear) {
            rootView.removeAllViews();
        }
        return rootView;
    }
}
