package com.sinata.framework.fragment.home;

import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.sinata.common.ui.component.HiBaseFragment;
import com.sinata.framework.R;
import com.sinata.framework.model.TabCategory;
import com.sinata.hi_ui.bottom.HiTabBottomLayout;
import com.sinata.hi_ui.top.HiTabTopInfo;
import com.sinata.hi_ui.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:50 PM
 * @Describe:
 */
public class HomePageFragment extends HiBaseFragment {
    private int selectTabIndex = 0;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_home;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        HomeViewModel homeViewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        homeViewModel.queryTabList().observe(getViewLifecycleOwner(), tabCategories -> {
            updateUI(tabCategories, view);
        });
        HiTabBottomLayout.clipBottomPadding(view.findViewById(R.id.view_pager));
//        queryTabList(view);
    }

//    private void queryTabList(View view) {
//        ApiFactory.INSTANCE.create(HomeApi.class).queryTabList().enqueue(new HiCallback<List<TabCategory>>() {
//            @Override
//            public void onSuccess(@NotNull HiResponse<List<TabCategory>> response) {
//                List<TabCategory> data = response.getData();
//                if (response.successful() && data != null) {
//                    updateUI(data, view);
//                }
//            }
//
//            @Override
//            public void onFailed(@NotNull Throwable throwable) {
//
//            }
//        });
//    }

    private static class CustomOnPageChangeListener implements ViewPager.OnPageChangeListener {
        private int topTabSelectIndex = 0;
        private HiTabTopLayout topTabLayout;
        private List<HiTabTopInfo<?>> topTabs;

        public CustomOnPageChangeListener(HiTabTopLayout topTabLayout, List<HiTabTopInfo<?>> topTabs) {
            this.topTabLayout = topTabLayout;
            this.topTabs = topTabs;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            if (position != topTabSelectIndex) {
                topTabLayout.defaultSelected(topTabs.get(position));
                topTabSelectIndex = position;
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }


    private void updateUI(List<TabCategory> data, View view) {
        if (!isAlive()) {
            return;
        }
        List<HiTabTopInfo<?>> topTabs = new ArrayList<>();
        data.stream().forEach(tabCategory -> {
            int defaultColor = ContextCompat.getColor(getContext(), R.color.color_333);
            int selectColor = ContextCompat.getColor(getContext(), R.color.color_dd2);
            HiTabTopInfo<Integer> tabTopInfo = new HiTabTopInfo(tabCategory.getCategoryName(), defaultColor, selectColor);
            topTabs.add(tabTopInfo);
        });
        HiTabTopLayout topTabLayout = view.findViewById(R.id.top_tab_layout);
        topTabLayout.inflateInfo(topTabs);
        topTabLayout.defaultSelected(topTabs.get(selectTabIndex));
        ViewPager viewPager = view.findViewById(R.id.view_pager);
        topTabLayout.addTabSelectedChangeListener((index, preInfo, nextInfo) -> {
            if (viewPager.getCurrentItem() == index) {
                viewPager.setCurrentItem(index, false);
            }
        });

        if (viewPager.getAdapter() == null) {
            viewPager.setAdapter(new HomePagerAdapter(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT));
        }
        ((HomePagerAdapter) viewPager.getAdapter()).update(data);
        viewPager.addOnPageChangeListener(new CustomOnPageChangeListener(topTabLayout, topTabs));
    }

    public static class HomePagerAdapter extends FragmentPagerAdapter {
        SparseArray<Fragment> fragments;
        List<TabCategory> tabs;
        private List<TabCategory> data;

        public HomePagerAdapter(@NonNull FragmentManager fm, int behavior) {
            super(fm, behavior);
            fragments = new SparseArray<>(tabs.size());
        }

        @NonNull
        @Override
        public Fragment getItem(int position) {
            String categoryId = tabs.get(position).getCategoryId();
            int categoryIdKey = Integer.parseInt(categoryId);
            Fragment fragment = fragments.get(categoryIdKey);
            if (fragment == null) {
                fragment = HomeTabFragment.Companion.newInstance(tabs.get(position).getCategoryId());
                fragments.put(categoryIdKey, fragment);
            }
            return fragment;
        }

        @Override
        public int getCount() {
            return tabs.size();
        }

        @Override
        public int getItemPosition(@NonNull Object object) {
            //需要判断刷新前后，两次fragment中的viewPager中的位置有改变，如果改变了PagerAdapter.POSITION_NONE,否则返回PagerAdapter.POSITION
            //是为了避免缓存数据和接口数据返回的顶部导航栏数据一样的情况，导致页面的fragment会先detach在attach，重复执行生命周期
            //同时还能兼顾缓存数据返回的顶部导航栏和接口返回的数据不一致的情况
            //拿到刷新前 该位置对应的Fragment对象
            int indexOfValue = fragments.indexOfValue((Fragment) object);
            Fragment fragment = getItem(indexOfValue);
            if (fragment.equals((Fragment) object)) {
                return PagerAdapter.POSITION_UNCHANGED;
            }
            return PagerAdapter.POSITION_NONE;
        }


        @Override
        public long getItemId(int position) {
            String categoryId = tabs.get(position).getCategoryId();
            return Integer.parseInt(categoryId);
        }

        public void update(List<TabCategory> tabCategories) {
            data.clear();
            if (tabCategories.size() > 0) {
                data.addAll(tabCategories);
            }
            notifyDataSetChanged();
        }
    }


}
