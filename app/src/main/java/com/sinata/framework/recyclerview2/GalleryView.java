package com.sinata.framework.recyclerview2;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author cjq
 * @Date 12/5/2021
 * @Time 11:34 PM
 * @Describe:
 */
class GalleryView extends ViewGroup {
    public GalleryView(@NonNull Context context) {
        super(context);
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GalleryView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }




    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }
}
