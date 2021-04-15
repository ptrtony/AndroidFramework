package com.sinata.framework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.fragment.CategoryFragment;
import com.sinata.framework.fragment.CollectFragment;
import com.sinata.framework.fragment.HomePageFragment;
import com.sinata.framework.fragment.MeFragment;
import com.sinata.framework.fragment.RecommendFragment;
import com.sinata.framework.log.utils.HiDisplayUtil;
import com.sinata.framework.logic.MainActivityLogic;
import com.sinata.hi_ui.bottom.HiTabBottom;
import com.sinata.hi_ui.bottom.HiTabBottomInfo;
import com.sinata.hi_ui.bottom.HiTabBottomLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:55 PM
 * @Describe:
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {

    private MainActivityLogic mainActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainActivityLogic = new MainActivityLogic(this);
        List<HiTabBottomInfo<?>> hiTabBottomInfos = new ArrayList<>();

        HiTabBottomInfo homeInfo = new HiTabBottomInfo(
                "首页",
                "fonts/iconfont.ttf",
                getString(R.string.if_home),
                null,
                "#ff656667",
                "#ffd44949"
        );
        homeInfo.fragment = HomePageFragment.class;
        HiTabBottomInfo infoRecommend = new HiTabBottomInfo(
                "收藏",
                "fonts/iconfont.ttf",
                getString(R.string.if_favorite),
                null,
                "#ff656667",
                "#ffd44949"
        );
        infoRecommend.fragment = CollectFragment.class;

//        val infoCategory = HiTabInfo(
//            "分类",
//            "fonts/iconfont.ttf",
//            getString(R.string.if_category),
//            null,
//            "#ff656667",
//            "#ffd44949"
//        )
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fire, null);

        HiTabBottomInfo infoCategory = new HiTabBottomInfo<String>(
                "分类",
                bitmap,
                bitmap
        );
        infoCategory.fragment = CategoryFragment.class;
        HiTabBottomInfo infoChat = new HiTabBottomInfo(
                "推荐",
                "fonts/iconfont.ttf",
                getString(R.string.if_recommend),
                null,
                "#ff656667",
                "#ffd44949"
        );
        infoChat.fragment = RecommendFragment.class;
        HiTabBottomInfo infoProfile = new HiTabBottomInfo(
                "我的",
                "fonts/iconfont.ttf",
                getString(R.string.if_profile),
                null,
                "#ff656667",
                "#ffd44949"
        );
        infoProfile.fragment = MeFragment.class;
        hiTabBottomInfos.add(homeInfo);
        hiTabBottomInfos.add(infoRecommend);
        hiTabBottomInfos.add(infoCategory);
        hiTabBottomInfos.add(infoChat);
        hiTabBottomInfos.add(infoProfile);
        HiTabBottomLayout hiTabBottomLayout = findViewById(R.id.hi_tab_bottom_layout);
        hiTabBottomLayout.inflateInfo(hiTabBottomInfos);
        hiTabBottomLayout.setBottomAlpha(0.8f);

        HiTabBottom hiTabBottom = hiTabBottomLayout.findTab(hiTabBottomInfos.get(2));
        hiTabBottom.resetTabHeight(HiDisplayUtil.dp2px(this, 66));
    }
}
