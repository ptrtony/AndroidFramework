package com.sinata.hi_ui.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinata.hi_ui.R;

/**
 * @author cjq
 * @Date 17/4/2021
 * @Time 8:27 PM
 * @Describe:
 */
public class HiTextOverView extends HiOverView{
    private ImageView mIvRotate;
    private TextView mText;
    private Animation mAnimation;
    public HiTextOverView(Context context) {
        super(context);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HiTextOverView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.hi_refresh_over_view,this,false);
        mIvRotate = findViewById(R.id.iv_rotate);
        mText = findViewById(R.id.text);
    }

    @Override
    public void onScroll(int Y, int pullRefreshHeight) {

    }

    @Override
    public void onVisible() {
        mText.setText("下拉刷新");
    }

    @Override
    public void onOver() {
        mText.setText("松开刷新");
    }

    @Override
    public void onRefresh() {
        mText.setText("正在刷新");
        mAnimation = AnimationUtils.loadAnimation(getContext(),R.anim.rotate_anim);
        LinearInterpolator ll = new LinearInterpolator();
        mAnimation.setInterpolator(ll);
        mIvRotate.startAnimation(mAnimation);
    }

    @Override
    public void onFinish() {
        mIvRotate.clearAnimation();
    }
}
