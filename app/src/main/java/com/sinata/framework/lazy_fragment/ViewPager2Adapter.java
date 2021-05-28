package com.sinata.framework.lazy_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

/**
 * @author cjq
 * @Date 11/5/2021
 * @Time 8:55 PM
 * @Describe:
 */
public class ViewPager2Adapter extends FragmentStateAdapter {

    List<Fragment> mFragments;
    public ViewPager2Adapter(@NonNull FragmentActivity fragmentActivity,List<Fragment> fragments) {
        super(fragmentActivity);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getItemCount() {
        return mFragments.size();
    }
}
