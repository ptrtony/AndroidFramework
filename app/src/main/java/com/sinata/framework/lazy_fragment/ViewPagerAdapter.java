package com.sinata.framework.lazy_fragment;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

/**
 * @author cjq
 * @Date 11/5/2021
 * @Time 9:19 PM
 * @Describe:
 */
public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private List<Fragment> mFragments;

    public ViewPagerAdapter(@NonNull FragmentManager fm,List<Fragment> fragments) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.mFragments = fragments;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return mFragments.get(position);
    }

    @Override
    public int getCount() {
        return mFragments.size();
    }
}
