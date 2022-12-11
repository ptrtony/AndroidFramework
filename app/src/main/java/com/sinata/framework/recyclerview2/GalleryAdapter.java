package com.sinata.framework.recyclerview2;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sinata.framework.R;
import com.sinata.framework.recyclerview2.bean.ImageGalleryBean;
import com.sinata.hi_library.utils.HiDisplayUtil;

import java.util.List;

/**
 * @author cjq
 * @Date 12/5/2021
 * @Time 2:35 PM
 * @Describe:
 */
public class GalleryAdapter<T> extends RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder> {

    private List<T> imageGalleryBeans;
    private LayoutInflater mInflater;
    private SparseArray<View> cacheViews = new SparseArray<>();
    private int RADIUS = 100;

    public SparseArray<View> getCacheViews() {
        return cacheViews;
    }

    public GalleryAdapter(Context context, List<T> imageGalleryBeans) {
        this.imageGalleryBeans = imageGalleryBeans;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item_gallery, parent, false);
        return new GalleryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GalleryViewHolder holder, int position) {
        ImageGalleryBean imageGalleryBean = (ImageGalleryBean) imageGalleryBeans.get(position);
        Glide.with(holder.itemView.getContext()).load(imageGalleryBean.path).into(holder.mIvGallery);
        holder.itemView.setOnClickListener(v -> {
            if (onItemClickListener != null) {
                onItemClickListener.onItemClick(v, position, (T) imageGalleryBean);
            }
            int[] location = new int[2];
            holder.mIvGallery.getLocationOnScreen(location);
            int screenWidth = HiDisplayUtil.getScreenWidth((Activity) holder.itemView.getContext());
            ObjectAnimator animator;
            if (location[0] - screenWidth / 2f > 0) {
                animator = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, screenWidth - location[0] - holder.mIvGallery.getWidth() / 2f);
            } else {
                animator = ObjectAnimator.ofFloat(holder.itemView, "translationX", 0, -location[0] - holder.mIvGallery.getWidth() / 2f);
            }
            animator.setDuration(1000);
            animator.start();
        });
        cacheViews.put(position, holder.itemView);
    }

    @Override
    public int getItemCount() {
        return imageGalleryBeans == null ? 0 : imageGalleryBeans.size();
    }


    public static class GalleryViewHolder extends RecyclerView.ViewHolder {
        ImageView mIvGallery;

        public GalleryViewHolder(@NonNull View itemView) {
            super(itemView);
            mIvGallery = itemView.findViewById(R.id.riv_gallery);
        }
    }


    private OnItemClickListener<T> onItemClickListener;

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T data);
    }
}
