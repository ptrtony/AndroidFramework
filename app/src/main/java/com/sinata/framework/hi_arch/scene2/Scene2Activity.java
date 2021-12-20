package com.sinata.framework.hi_arch.scene2;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.ViewModelProvider;

import com.sinata.framework.R;
import com.sinata.framework.databinding.ActivityScene2Binding;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/10/10
 */
public class Scene2Activity extends AppCompatActivity {

    private static final String TAG = "Scene2Activity";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityScene2Binding binding = DataBindingUtil.setContentView(this, R.layout.activity_scene2);
        HomeViewModel homeViewModel = new HomeViewModel();
        binding.setViewModel(homeViewModel);
        homeViewModel.getUserInfo();
//        binding.setViewModel();
        binding.address.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.e(TAG,"onTextChanged:" + homeViewModel.mInfo.get().getAddress());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

}
