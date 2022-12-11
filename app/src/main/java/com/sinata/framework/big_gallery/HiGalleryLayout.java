package com.sinata.framework.big_gallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.sinata.hi_library.utils.HiDisplayUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 24/5/2021
 * @Time 10:05 AM
 * @Describe:
 */
public class HiGalleryLayout extends ViewGroup {
    private static final int PADDING = HiDisplayUtil.dp2Px(5);
    private static final int childrenWidth = HiDisplayUtil.dp2Px(80);
    List<Rect> childrenBounds = new ArrayList<>();
    SparseArray<HiGalleryView> hiGalleryViews = new SparseArray<>();
    public HiGalleryLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthUsed = 0;
        int lineWidthUsed = 0;
        int heightUsed = 0;
        int lineHeight = 0;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int horizenCount=0;
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            if (widthMode!=MeasureSpec.UNSPECIFIED &&
                    lineWidthUsed+childView.getMeasuredWidth() + horizenCount*PADDING >widthSize){
                lineWidthUsed = 0;
                horizenCount = 0;
                heightUsed += lineHeight;
                measureChildWithMargins(childView, widthMeasureSpec, 0, heightMeasureSpec, heightUsed);
            }
            horizenCount ++;
            Rect childrenBound;
            if (childrenBounds.size() <= i) {
                childrenBound = new Rect();
                childrenBounds.add(childrenBound);
            } else {
                childrenBound = childrenBounds.get(i);
            }

            childrenBound.set(lineWidthUsed, heightUsed, lineWidthUsed + childView.getMeasuredWidth()+PADDING, heightUsed + childView.getMeasuredHeight()+PADDING);
            lineWidthUsed += (childView.getMeasuredWidth()+horizenCount*PADDING);
            widthUsed = Math.max(lineWidthUsed,widthUsed);
            lineHeight = Math.max(lineHeight, childView.getMeasuredHeight()+PADDING);
        }
        int measureWidth = widthUsed;
        heightUsed += lineHeight;
        int measureHeight = heightUsed;
        setMeasuredDimension(measureWidth, measureHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i++) {
            View childView = getChildAt(i);
            Rect childrenBound = childrenBounds.get(i);
            childView.layout(childrenBound.left+PADDING, childrenBound.top + PADDING, childrenBound.right, childrenBound.bottom);
            HiGalleryView hiGalleryView = hiGalleryViews.get(i);
            HiGalleryInfo hiGalleryInfo = hiGalleryView.getHiGalleryInfo();
            hiGalleryInfo.preBound.set(childrenBound.left+PADDING, childrenBound.top + PADDING, childrenBound.right, childrenBound.bottom);
            hiGalleryView.setHiGalleryInfo(hiGalleryInfo);
            hiGalleryViews.setValueAt(i,hiGalleryView);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attr) {
        return new MarginLayoutParams(getContext(), attr);
    }

    private int position;
    public void addView(List<String> imagePaths){
        for (int i = 0; i < imagePaths.size(); i++) {
            position = i;
            Glide.with(getContext()).asBitmap().load(imagePaths).into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    HiGalleryInfo hiGalleryInfo = new HiGalleryInfo();
                    HiGalleryView hiGalleryView = new HiGalleryView(getContext());
                    hiGalleryView.setHiGalleryInfo(hiGalleryInfo);
                    hiGalleryView.setImageBitmap(resource);
                    hiGalleryViews.put(position,hiGalleryView);
                    int bitmapWidth = resource.getWidth();
                    int bitmapHeight = resource.getHeight();
                    LayoutParams params = new LayoutParams(childrenWidth, (int) (bitmapHeight * bitmapWidth / (childrenWidth * 1.0f)));
                    addView(hiGalleryView,params);
                }
            });
        }
        requestLayout();
    }


}
