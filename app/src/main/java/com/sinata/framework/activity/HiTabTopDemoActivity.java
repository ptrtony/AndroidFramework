package com.sinata.framework.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.R;
import com.sinata.hi_library.utils.HiDisplayUtil;
import com.sinata.hi_ui.search.HiSearchView;
import com.sinata.hi_ui.tab.common.IHiTabLayout;
import com.sinata.hi_ui.title.HiNavigationBar;
import com.sinata.hi_ui.top.HiTabTopInfo;
import com.sinata.hi_ui.top.HiTabTopLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @author cjq
 * @Date 16/4/2021
 * @Time 10:56 PM
 * @Describe:
 */
public class HiTabTopDemoActivity extends HiBaseActivity {
    String[] tabsStr = new String[]{
            "热门",
            "服装",
            "数码",
            "鞋子",
            "零食",
            "家电",
            "汽车",
            "百货",
            "家居",
            "装修",
            "运动"
    };

    private List<HiTabTopInfo<?>> infoList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_tab_top_demo);
        initActionBar();
        initTabTop();
    }

    private void initActionBar() {
        HiNavigationBar hiNavigationBar = findViewById(R.id.action_bar);
        hiNavigationBar.setNavListener(v -> {
        });
        hiNavigationBar.addRightTextButton("搜索", View.generateViewId());

        HiSearchView searchView = new HiSearchView(this);
        searchView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, HiDisplayUtil.dp2Px(58f)));
        searchView.postDelayed(() -> searchView.setKeyword("iphone", v -> {

        }), 2000);
        searchView.setHintText("搜索你想要的商品¥");
        hiNavigationBar.setCenterView(searchView);
    }

    private void initTabTop() {
        HiTabTopLayout hiTabTopLayout = findViewById(R.id.hi_tab_bottom_layout);
        int tabBottomDefaultColor = getResources().getColor(R.color.tabBottomDefaultColor);
        int tabBottomTintColor = getResources().getColor(R.color.tabBottomTintColor);
        for (int i = 0; i < tabsStr.length; i++) {
            infoList.add(new HiTabTopInfo<>(tabsStr[i], tabBottomDefaultColor, tabBottomTintColor));
        }
        hiTabTopLayout.inflateInfo(infoList);
        hiTabTopLayout.defaultSelected(infoList.get(0));
        hiTabTopLayout.addTabSelectedChangeListener(new IHiTabLayout.OnTabSelectedListener<HiTabTopInfo<?>>() {
            @Override
            public void onTabSelectedChange(int index, @NonNull HiTabTopInfo<?> preInfo, @NonNull HiTabTopInfo<?> nextInfo) {
                Toast.makeText(HiTabTopDemoActivity.this, nextInfo.name, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
