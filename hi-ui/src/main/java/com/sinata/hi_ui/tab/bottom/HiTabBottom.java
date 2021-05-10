package com.sinata.hi_ui.tab.bottom;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.sinata.hi_ui.R;
import com.sinata.hi_ui.tab.common.IHiTab;

/**
 * @author cjq
 * @Date 10/5/2021
 * @Time 6:03 PM
 * @Describe:
 */
public class HiTabBottom extends RelativeLayout implements IHiTab<HiTabBottomInfo<?>> {
    private  HiTabBottomInfo<?> tabInfo;
    private ImageView tabImageView;
    private TextView tabIconView;
    private TextView tabNameView;

    public HiTabBottom(Context context) {
        this(context,null);
    }

    public HiTabBottom(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public HiTabBottom(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public void init(Context context){
        LayoutInflater.from(context).inflate(R.layout.hi_tab_bottom,this,false);
        tabImageView = findViewById(R.id.iv_image);
        tabIconView = findViewById(R.id.tv_icon);
        tabNameView = findViewById(R.id.tv_name);
    }

    @Override
    public void setHiTabInfo(@NonNull HiTabBottomInfo<?> data) {
        this.tabInfo = data;
        inflateTabInfo(false,true);
    }

    private void inflateTabInfo(boolean selected, boolean init) {
        if (tabInfo.tabType == TabType.ICON){
            if (init){
                tabImageView.setVisibility(View.GONE);
                tabIconView.setVisibility(View.VISIBLE);
                Typeface typeface = Typeface.createFromAsset(getResources().getAssets(),tabInfo.iconFont);
                tabIconView.setTypeface(typeface);
                if (!tabInfo.name.isEmpty()){
                    tabNameView.setText(tabInfo.name);
                }
            }

            if (selected){
                if (tabInfo.selectIconName.isEmpty()){
                    tabIconView.setText( tabInfo.defaultIconName);
                }else{
                    tabIconView.setText( tabInfo.selectIconName);
                }
                if (tabInfo.tintColor instanceof String){
                    tabIconView.setTextColor(Color.parseColor((String) tabInfo.tintColor));
                    tabNameView.setTextColor(Color.parseColor((String) tabInfo.tintColor));
                }else{
                    tabIconView.setTextColor((Integer) tabInfo.tintColor);
                    tabNameView.setTextColor((Integer) tabInfo.tintColor);
                }

            }else{
                tabIconView.setText(tabInfo.defaultIconName);
                if (tabInfo.defaultColor instanceof String){
                    tabIconView.setTextColor(Color.parseColor((String) tabInfo.defaultColor));
                    tabNameView.setTextColor(Color.parseColor((String) tabInfo.defaultColor));
                }else{
                    tabIconView.setTextColor((Integer) tabInfo.defaultColor);
                    tabNameView.setTextColor((Integer) tabInfo.defaultColor);
                }

            }
        }else if (tabInfo.tabType == TabType.BITMAP){
            if (init){
                tabImageView.setVisibility(View.VISIBLE);
                tabIconView.setVisibility(View.GONE);
                if (!tabInfo.name.isEmpty()){
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

    @Override
    public void resetTabHeight(int height) {
        LayoutParams params = (LayoutParams) getLayoutParams();
        params.height = height;
        setLayoutParams(params);
        tabNameView.setVisibility(View.GONE);
    }

    @Override
    public void onTabSelectedChange(int index, @NonNull HiTabBottomInfo<?> preInfo, @NonNull HiTabBottomInfo<?> nextInfo) {
        if (preInfo != tabInfo && nextInfo != tabInfo || preInfo == nextInfo){
            return;
        }
        inflateTabInfo(preInfo != tabInfo,false);
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
}
