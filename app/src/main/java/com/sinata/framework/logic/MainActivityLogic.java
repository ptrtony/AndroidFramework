package com.sinata.framework.logic;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:59 PM
 * @Describe:
 */

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import com.sinata.common.tab.HiFragmentTabView;
import com.sinata.common.tab.HiTabViewAdapter;
import com.sinata.framework.R;
import com.sinata.framework.fragment.CategoryFragment;
import com.sinata.framework.fragment.CollectFragment;
import com.sinata.framework.fragment.HomePageFragment;
import com.sinata.framework.fragment.MeFragment;
import com.sinata.framework.fragment.RecommendFragment;
import com.sinata.hi_library.log.utils.HiDisplayUtil;
import com.sinata.hi_ui.bottom.HiTabBottomLayout;
import com.sinata.hi_ui.tab.bottom.HiTabBottom;
import com.sinata.hi_ui.tab.bottom.HiTabBottomInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 将MainActivity的一些逻辑内聚到这里
 */
public class MainActivityLogic {
    private HiFragmentTabView mHiFragmentTabView;
    private HiTabBottomLayout mHiTabBottomLayout;
    private List<HiTabBottomInfo<?>> infoList;
    private ActivityProvider activityProvider;
    private int currentItemIndex = 0;
    private static final String SAVED_CURRENT_ID = "SAVED_CURRENT_ID";

    public MainActivityLogic(ActivityProvider activityProvider, Bundle savedInstanceState) {
        this.activityProvider = activityProvider;
        if (savedInstanceState != null) {
            currentItemIndex = savedInstanceState.getInt(SAVED_CURRENT_ID);
        }
        initTabBottom();
    }


    public HiFragmentTabView getHiFragmentTabView() {
        return mHiFragmentTabView;
    }

    public void setHiFragmentTabView(HiFragmentTabView hiFragmentTabView) {
        this.mHiFragmentTabView = hiFragmentTabView;
    }

    public HiTabBottomLayout getHiTabBottomLayout() {
        return mHiTabBottomLayout;
    }

    public void setHiTabBottomLayout(HiTabBottomLayout hiTabBottomLayout) {
        this.mHiTabBottomLayout = hiTabBottomLayout;
    }

    private void initTabBottom() {
        mHiTabBottomLayout = activityProvider.findViewById(R.id.hi_tab_bottom_layout);
        mHiTabBottomLayout.setBottomAlpha(0.8f);
        infoList = new ArrayList<>();
        String defaultColor = "#ff656667";
        String tintColor = "#ffd44949";
        HiTabBottomInfo homeInfo = new HiTabBottomInfo(
                "首页",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_home),
                null,
                defaultColor,
                tintColor
        );
        homeInfo.fragment = HomePageFragment.class;
        HiTabBottomInfo infoRecommend = new HiTabBottomInfo(
                "收藏",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_favorite),
                null,
                defaultColor,
                tintColor
        );
        infoRecommend.fragment = CollectFragment.class;

        Bitmap bitmap = BitmapFactory.decodeResource(activityProvider.getResources(), R.drawable.fire, null);

        HiTabBottomInfo infoCategory = new HiTabBottomInfo<String>(
                "分类",
                bitmap,
                bitmap
        );
        infoCategory.fragment = CategoryFragment.class;
        HiTabBottomInfo infoChat = new HiTabBottomInfo(
                "推荐",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_recommend),
                null,
                defaultColor,
                tintColor
        );
        infoChat.fragment = RecommendFragment.class;
        HiTabBottomInfo infoProfile = new HiTabBottomInfo(
                "我的",
                "fonts/iconfont.ttf",
                activityProvider.getString(R.string.if_profile),
                null,
                defaultColor,
                tintColor
        );
        infoProfile.fragment = MeFragment.class;
        infoList.add(homeInfo);
        infoList.add(infoRecommend);
        infoList.add(infoCategory);
        infoList.add(infoChat);
        infoList.add(infoProfile);
        mHiTabBottomLayout.inflateInfo(infoList);
        initFragmentTabView();
        mHiTabBottomLayout.addTabSelectedChangeListener((index, preInfo, nextInfo) -> {
            mHiFragmentTabView.setCurrentItem(index);
            MainActivityLogic.this.currentItemIndex = index;
        });

        mHiTabBottomLayout.defaultSelected(infoList.get(MainActivityLogic.this.currentItemIndex));
        mHiTabBottomLayout.setBottomAlpha(0.8f);
        HiTabBottom mHiTabBottom = mHiTabBottomLayout.findTab(infoList.get(2));
        mHiTabBottom.resetTabHeight(HiDisplayUtil.dp2px(activityProvider.getResources(), 66));
    }

    private void initFragmentTabView() {
        HiTabViewAdapter hiTabViewAdapter = new HiTabViewAdapter(activityProvider.getSupportFragmentManager(), infoList);
        mHiFragmentTabView = activityProvider.findViewById(R.id.fragment_tab_view);
        mHiFragmentTabView.setAdapter(hiTabViewAdapter);
    }

    public void onSaveInstanceState(Bundle outState) {
        if (outState!=null){
            outState.putInt(SAVED_CURRENT_ID,currentItemIndex);
        }
    }


    public interface ActivityProvider {
        <T extends View> T findViewById(@IdRes int id);

        Resources getResources();

        FragmentManager getSupportFragmentManager();

        String getString(@StringRes int resId);
    }

}
