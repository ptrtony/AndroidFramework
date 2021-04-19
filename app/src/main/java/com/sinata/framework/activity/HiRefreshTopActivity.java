package com.sinata.framework.activity;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.R;
import com.sinata.hi_ui.refresh.HiRefreshLayout;
import com.sinata.hi_ui.refresh.HiTextOverView;

/**
 * @author cjq
 * @Date 17/4/2021
 * @Time 8:40 PM
 * @Describe:
 */
public class HiRefreshTopActivity extends HiBaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hi_refresh_top);
        HiRefreshLayout hiRefreshLayout = findViewById(R.id.hi_refresh_layout);
        hiRefreshLayout.setRefreshOverView(new HiTextOverView(this));
    }
}
