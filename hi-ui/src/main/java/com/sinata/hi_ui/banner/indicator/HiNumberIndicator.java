package com.sinata.hi_ui.banner.indicator;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinata.hi_library.utils.HiDisplayUtil;
import com.sinata.hi_ui.R;

/**
 * @author cjq
 * @Date 24/4/2021
 * @Time 6:12 PM
 * @Describe:
 */
public class HiNumberIndicator extends FrameLayout implements HiIndicator<FrameLayout> {

    private static final int VWC = LayoutParams.WRAP_CONTENT;
    /**
     * 指示器点左右间距
     */
    private int mPointLeftRightPadding;

    /**
     * 指示器点上下内间距
     */
    private int mPointTopBottomPadding;

    /**
     * 定义数字指示器的颜色
     */
    private int textColor = R.color.white;

    /**
     * 定义数字指示器的字体大小
     */
    private int textSize;

    private int currentCount = 1;

    public HiNumberIndicator(@NonNull Context context) {
        this(context, null);
    }

    public HiNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HiNumberIndicator(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPointLeftRightPadding = HiDisplayUtil.dp2px(getContext().getResources(), 10);
        mPointTopBottomPadding = HiDisplayUtil.dp2px(getContext().getResources(), 10);
        textSize = HiDisplayUtil.sp2px(getContext().getResources(), 8);
    }

    @Override
    public FrameLayout get() {
        return this;
    }

    @Override
    public void onInflate(int count) {
        removeAllViews();
        if (count <= 0) return;
        TextView textView = new TextView(getContext());
        textView.setTextColor(getContext().getResources().getColor(textColor));
        textView.setTextSize(textSize);
        textView.setText(countChange(count));
        LayoutParams params = new LayoutParams(VWC, VWC);
        params.gravity = Gravity.RIGHT | Gravity.BOTTOM;
        params.setMargins(0, 0, mPointLeftRightPadding, mPointTopBottomPadding);
        textView.setLayoutParams(params);
        addView(textView);
    }

    @Override
    public void onPointChange(int current, int count) {
        this.currentCount = current + 1;
        TextView textView = (TextView) getChildAt(0);
        textView.setText(countChange(count));
    }

    private String countChange(int count) {
        return currentCount + "/" + count;
    }
}
