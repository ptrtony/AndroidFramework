package com.sinata.hi_ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinata.hi_library.log.utils.HiDisplayUtil;
import com.sinata.hi_ui.R;

/**
 * @author cjq
 * @Date 22/4/2021
 * @Time 8:49 AM
 * @Describe: 圆点指示器
 */
public class HiCircleIndicator_java extends FrameLayout implements HiIndicator<FrameLayout> {

    private static final int VWC = LayoutParams.WRAP_CONTENT;

    /**
     * 正常状态下的指示器
     */
    @DrawableRes
    private int mPointNormal = R.drawable.shape_point_normal;

    /**
     * 选中状态下的指示器
     */
    @DrawableRes
    private int mPointSelected = R.drawable.shape_point_select;

    /**
     * 指示器点左右间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示器点上下内间距
     */
    private int mPointTopBottomPadding;



    public HiCircleIndicator_java(@NonNull Context context) {
        this(context,null);
    }

    public HiCircleIndicator_java(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        init();
    }

    private void init() {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(getContext().getResources(),5);
        mPointTopBottomPadding = HiDisplayUtil.dp2px(getContext().getResources(),15);
    }

    public HiCircleIndicator_java(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0){
            return;
        }
        LinearLayout groupView = new LinearLayout(getContext());
        groupView.setOrientation(LinearLayout.HORIZONTAL);
        ImageView imageView;
        for (int i = 0; i < count; i++) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(VWC,VWC);
            imageView = new ImageView(getContext());
            params.setMargins(mPointLeftRightPadding,mPointTopBottomPadding,mPointLeftRightPadding,mPointTopBottomPadding);
            params.gravity = Gravity.CENTER_VERTICAL;
            imageView.setLayoutParams(params);
            groupView.addView(imageView);
            if (i == 0){
                imageView.setImageResource(mPointSelected);
            }else{
                imageView.setImageResource(mPointNormal);
            }
        }
        LayoutParams groupLayoutParams = new LayoutParams(VWC,VWC);
        groupLayoutParams.gravity = Gravity.CENTER | Gravity.BOTTOM;
        addView(groupView,groupLayoutParams);
    }

    @Override
    public void onPointChange(int current, int count) {
        ViewGroup viewGroup = (ViewGroup) getChildAt(0);
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            ImageView imageView = (ImageView) viewGroup.getChildAt(i);
            if (i == current){
                imageView.setImageResource(mPointSelected);
            }else{
                imageView.setImageResource(mPointNormal);
            }
            imageView.requestLayout();
        }
    }
}
