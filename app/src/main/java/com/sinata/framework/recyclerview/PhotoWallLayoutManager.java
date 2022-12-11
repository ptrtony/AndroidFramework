package com.sinata.framework.recyclerview;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import com.sinata.hi_library.utils.HiDisplayUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 16/5/2021
 * @Time 3:09 PM
 * @Describe:
 */
public class PhotoWallLayoutManager extends RecyclerView.LayoutManager {

    //每个item的定义
    public class Item {
        int useHeight;
        View view;

        public void setRect(Rect rect) {
            this.rect = rect;
        }

        Rect rect;

        public Item(int useHeight, View view, Rect rect) {
            this.useHeight = useHeight;
            this.view = view;
            this.rect = rect;
        }
    }

    //行信息的定义
    public class Row {
        public void setCuTop(float cuTop) {
            this.cuTop = cuTop;
        }

        public void setMaxHeight(float maxHeight) {
            this.maxHeight = maxHeight;
        }

        //每一行的头部坐标
        float cuTop;
        //每一行需要占据的最大高度
        float maxHeight;
        //每一行存储的item
        List<Item> views = new ArrayList<>();

        public void addViews(Item view) {
            views.add(view);
        }
    }


    public class ItemHolder {
        private Rect mRect;
        private View view;

        public ItemHolder(Rect mRect, View view) {
            this.mRect = mRect;
            this.view = view;
        }
    }


    public SparseArray<Rect> mColumns = new SparseArray<>();

    public SparseArray<ItemHolder> mItems = new SparseArray<>();

    public static int mRegionRadius = HiDisplayUtil.px2dp(200);

    private int mUseMaxHeight;
    private int mViewScreenWidth;
    //计算显示的内容的高度
    protected int totalHeight = 0;
    private Row row = new Row();
    private List<Row> lineRows = new ArrayList<>();
    //保存所有的Item的上下左右的偏移量信息
    private SparseArray<Rect> allItemFrames = new SparseArray<>();
    //竖直方向上的偏移量
    private int verticalScrollOffset = 0;
    final PhotoWallLayoutManager self = this;

    @Override
    public RecyclerView.LayoutParams generateDefaultLayoutParams() {
        return new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT);
    }

    @Override
    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
        super.onLayoutChildren(recycler, state);
        int maxHeightItem = 0;
        if (getChildCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (state.isPreLayout() || getChildCount() == 0) return;
        detachAndScrapAttachedViews(recycler);
        int topOffset = getPaddingTop();
        int bottomOffset = getPaddingBottom();
        int useWidth = 0;
        int useHeight = 0;
        mViewScreenWidth = getWidth();
        mUseMaxHeight = getHeight() - topOffset - bottomOffset;
        for (int i = 0; i < getChildCount(); i++) {
            View child = recycler.getViewForPosition(i);
            if (child.getVisibility() == View.GONE) continue;
            measureChildWithMargins(child, 0, 0);
            int childWidth = getDecoratedMeasuredWidth(child);
            int childHeight = getDecoratedMeasuredHeight(child);
            Rect rect = mColumns.get(i);
            if (rect == null) {
                rect = new Rect();
            }
            rect.set(useWidth, useHeight, useWidth + childWidth, useHeight + childHeight);
            mColumns.put(i, rect);
            ItemHolder item = mItems.get(i);
            if (item == null) {
                item = new ItemHolder(rect, child);
            }
            item.mRect = rect;
            item.view = child;
            mItems.put(i, item);
            if (mUseMaxHeight > useHeight + childHeight) {
                useHeight += childHeight;
            } else {
                useHeight = 0;
                useWidth += childWidth;
            }

            //不要忘了最后一行进行刷新下布局
            if (i == getItemCount() - 1) {
                formatAboveRow();
                totalHeight += maxHeightItem;
            }

        }
        totalHeight = Math.max(totalHeight, getVerticalSpace());
        fillLayout(recycler, state);

    }


    private void fillLayout(RecyclerView.Recycler recycler, RecyclerView.State state) {
        if (getChildCount() == 0) {
            detachAndScrapAttachedViews(recycler);
            return;
        }
        if (state.isPreLayout() || getChildCount() == 0) return;
        detachAndScrapAttachedViews(recycler);
        for (int i = 0; i < getChildCount(); i++) {
            View childView = recycler.getViewForPosition(i);
            Rect rect = mColumns.get(i);
            layoutDecoratedWithMargins(childView, rect.left, rect.top, rect.right, rect.bottom);
        }
    }


    /**
     * 计算每一行没有居中的viewgroup，让居中显示
     */
    private void formatAboveRow() {
        List<Item> views = row.views;
        for (int i = 0; i < views.size(); i++) {
            Item item = views.get(i);
            View view = item.view;
            int position = getPosition(view);
            //如果该item的位置不在该行中间位置的话，进行重新放置
            if (allItemFrames.get(position).top < row.cuTop + (row.maxHeight - views.get(i).useHeight) / 2) {
                Rect frame = allItemFrames.get(position);
                if (frame == null) {
                    frame = new Rect();
                }
                frame.set(allItemFrames.get(position).left, (int) (row.cuTop + (row.maxHeight - views.get(i).useHeight) / 2),
                        allItemFrames.get(position).right, (int) (row.cuTop + (row.maxHeight - views.get(i).useHeight) / 2 + getDecoratedMeasuredHeight(view)));
                allItemFrames.put(position, frame);
                item.setRect(frame);
                views.set(i, item);
            }
        }
        row.views = views;
        lineRows.add(row);
        row = new Row();
    }

    /**
     * 竖直方向需要滑动的条件
     *
     * @return
     */
    @Override
    public boolean canScrollVertically() {
        return true;
    }

    //监听竖直方向滑动的偏移量
    @Override
    public int scrollVerticallyBy(int dy, RecyclerView.Recycler recycler,
                                  RecyclerView.State state) {

        Log.d("TAG", "totalHeight:" + totalHeight);
        //实际要滑动的距离
        int travel = dy;

        //如果滑动到最顶部
        if (verticalScrollOffset + dy < 0) {//限制滑动到顶部之后，不让继续向上滑动了
            travel = -verticalScrollOffset;//verticalScrollOffset=0
        } else if (verticalScrollOffset + dy > totalHeight - getVerticalSpace()) {//如果滑动到最底部
            travel = totalHeight - getVerticalSpace() - verticalScrollOffset;//verticalScrollOffset=totalHeight - getVerticalSpace()
        }

        //将竖直方向的偏移量+travel
        verticalScrollOffset += travel;

        // 平移容器内的item
        offsetChildrenVertical(-travel);
        fillLayout(recycler, state);
        return travel;
    }

    private int getVerticalSpace() {
        return self.getHeight() - self.getPaddingBottom() - self.getPaddingTop();
    }

    public int getHorizontalSpace() {
        return self.getWidth() - self.getPaddingLeft() - self.getPaddingRight();
    }

    public void updateItemAnimation(int[] location) {
        if (mItems == null || mItems.size() == 0) return;
        int preWidth = mRegionRadius + (mItems.get(0).mRect.right - mItems.get(0).mRect.left) * 2;
        for (int i = 0; i < mItems.size(); i++) {
            ItemHolder itemHolder = mItems.get(i);
            View childView = itemHolder.view;
            Rect rect = itemHolder.mRect;
            if (childView != null && rect != null) {
                int mode = (int) (location[0] / (preWidth * 1.0));
                int fraction = preWidth + preWidth * mode;
                if (rect.left > fraction - preWidth && rect.right < fraction){
                    int centerX = (int) (fraction - preWidth / 2f);
                    int centerY = (int) (mUseMaxHeight/2f);
                    int edgeStartX = centerX - mRegionRadius - childView.getMeasuredWidth();
                    int edgeEndX = centerX + mRegionRadius + childView.getMeasuredWidth();
                    if ((rect.right + rect.left)/2f > centerX){
                        ObjectAnimator scale = ObjectAnimator.ofFloat(childView,"scaleX",1f,0.5f);
                        ObjectAnimator animation = ObjectAnimator.ofFloat(childView,"scaleX",1f,0.5f);
                    }else{

                    }




                }
            }
        }

    }

}
