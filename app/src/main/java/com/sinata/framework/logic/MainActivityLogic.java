package com.sinata.framework.logic;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:59 PM
 * @Describe:
 */

import android.content.res.Resources;
import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.StringRes;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.sinata.common.ui.component.HiFragmentTabView;
import com.sinata.framework.R;
import com.sinata.framework.ui.bottom.HiTabBottomInfo;
import com.sinata.framework.ui.bottom.HiTabBottomLayout;

import java.util.List;

/**
 * 将MainActivity的一些逻辑内聚到这里
 */
public class MainActivityLogic {
    private HiFragmentTabView mHiFragmentTabView;
    private HiTabBottomLayout mHiTabBottomLayout;
    private List<HiTabBottomInfo<?>> mTabInfo;
    private ActivityProvider activityProvider;
    private int currentItemIndex;

    public MainActivityLogic(ActivityProvider activityProvider) {
        this.activityProvider = activityProvider;
        initTabBottom();
    }

    private void initTabBottom() {
        activityProvider.findViewById(R.id.hi_tab_bottom_layout);
    }

    public interface ActivityProvider{
        <T extends View> T findViewById(@IdRes int id);
        Resources getResources();
        FragmentManager getSupportFragmentManager();
        String getString(@StringRes int resId);
    }

}
