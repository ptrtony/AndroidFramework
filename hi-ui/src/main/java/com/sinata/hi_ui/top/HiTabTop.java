package com.sinata.hi_ui.top;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.sinata.hi_ui.R;
import com.sinata.hi_ui.tab.common.IHiTab;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 10:08 PM
 * @Describe:
 */
public class HiTabTop extends RelativeLayout implements IHiTab<HiTabTopInfo<?>> {

    private HiTabTopInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabNameView;
    private View indicator;
    public HiTabTop(Context context) {
        this(context, null);
    }

    public HiTabTop(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabTop(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_top, this);
        tabImageView = findViewById(R.id.iv_image);
        tabNameView = findViewById(R.id.tv_name);
        indicator = findViewById(R.id.tab_top_indicator);
    }

    @Override
    public void setHiTabInfo(@NonNull HiTabTopInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == HiTabTopInfo.TabType.TEXT) {
            if (init){
                tabImageView.setVisibility(GONE);
                tabNameView.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }

            if (selected) {
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
                indicator.setVisibility(View.VISIBLE);
            }else{
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
                indicator.setVisibility(View.GONE);
            }
        }else if (tabInfo.tabType == HiTabTopInfo.TabType.BITMAP){
            if (init){
                tabImageView.setVisibility(VISIBLE);
                tabNameView.setVisibility(VISIBLE);
                if (!TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected){
                tabImageView.setImageBitmap(tabInfo.selectBitmap);
                indicator.setVisibility(View.VISIBLE);
            }else{
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
                indicator.setVisibility(View.GONE);
            }
        }

    }

    /**
     * 改变某个icon高度
     * @param height
     */
    @Override
    public void resetTabHeight(int height) {
        ViewGroup.LayoutParams params =  getLayoutParams();
        params.height = height;
        setLayoutParams(params);
        getTabNameView().setVisibility(View.GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabTopInfo<?> preInfo, @NonNull HiTabTopInfo<?> nextInfo) {
        if (preInfo != tabInfo && nextInfo != tabInfo || preInfo == nextInfo){
            return;
        }

        if (preInfo == tabInfo){
            inflateInfo(false,false);
        }else{
            inflateInfo(true,false);
        }
    }

    public HiTabTopInfo<?> getTabInfo() {
        return tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public void setTabImageView(ImageView tabImageView) {
        this.tabImageView = tabImageView;
    }


    public TextView getTabNameView() {
        return tabNameView;
    }

    public void setTabNameView(TextView tabNameView) {
        this.tabNameView = tabNameView;
    }

    @ColorInt
    private int getTextColor(Object color) {
        if (color instanceof String) {
            return Color.parseColor((String) color);
        }

        return (int) color;
    }
}
