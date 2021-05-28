package com.sinata.framework.recyclerview2;

import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

/**
 * @author cjq
 * @Date 12/5/2021
 * @Time 5:05 PM
 * @Describe:
 */
public class DefaultLayoutManager extends RecyclerView.LayoutManager {



    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    }


    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);

    }
}
