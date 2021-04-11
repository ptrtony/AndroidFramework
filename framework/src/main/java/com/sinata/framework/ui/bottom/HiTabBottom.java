package com.sinata.framework.ui.bottom;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;

import com.sinata.framework.R;
import com.sinata.framework.ui.tab.common.IHiTab;

/**
 * @author cjq
 * @Date 11/4/2021
 * @Time 10:08 PM
 * @Describe:
 */
public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {

    private HiTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public HiTabBottom(Context context) {
        this(context, null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_tab_bottom, this);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);

    }

    @Override
    public void setHiTabInfo(@NonNull HiTabBottomInfo<?> data) {
        this.tabInfo = data;
        inflateInfo(false, true);
    }

    private void inflateInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == HiTabBottomInfo.TabType.ICON) {
            if (init){
                tabImageView.setVisibility(GONE);
                tabIconView.setVisibility(VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getResources().getAssets(), tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                if (!TextUtils.isEmpty(tabInfo.name)) {
                    tabNameView.setText(tabInfo.name);
                }
            }

            if (selected) {
                tabIconView.setText(TextUtils.isEmpty(tabInfo.selectIconName) ? tabInfo.defaultIconName : tabInfo.selectIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.tintColor));
                tabNameView.setTextColor(getTextColor(tabInfo.tintColor));
            }else{
                tabIconView.setText(tabInfo.defaultIconName);
                tabIconView.setTextColor(getTextColor(tabInfo.defaultColor));
                tabNameView.setTextColor(getTextColor(tabInfo.defaultColor));
            }
        }else if (tabInfo.tabType == HiTabBottomInfo.TabType.BITMAP){
            if (init){
                tabImageView.setVisibility(VISIBLE);
                tabIconView.setVisibility(GONE);
                if (!TextUtils.isEmpty(tabInfo.name)){
                    tabNameView.setText(tabInfo.name);
                }
            }
            if (selected){
                tabImageView.setImageBitmap(tabInfo.selectBitmap);
            }else{
                tabImageView.setImageBitmap(tabInfo.defaultBitmap);
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
        getTabImageView().setVisibility(View.GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> preInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
        if (preInfo != tabInfo && nextInfo != tabInfo || preInfo == nextInfo){
            return;
        }

        if (preInfo == tabInfo){
            inflateInfo(false,false);
        }else{
            inflateInfo(true,false);
        }
    }

    public HiTabBottomInfo<?> getTabInfo() {
        return tabInfo;
    }

    public void setTabInfo(HiTabBottomInfo<?> tabInfo) {
        this.tabInfo = tabInfo;
    }

    public ImageView getTabImageView() {
        return tabImageView;
    }

    public void setTabImageView(ImageView tabImageView) {
        this.tabImageView = tabImageView;
    }

    public TextView getTabIconView() {
        return tabIconView;
    }

    public void setTabIconView(TextView tabIconView) {
        this.tabIconView = tabIconView;
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
