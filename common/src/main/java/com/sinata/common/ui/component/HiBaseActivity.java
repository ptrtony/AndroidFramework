package com.sinata.common.ui.component;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:08 PM
 * @Describe:
 */
public abstract class HiBaseActivity extends AppCompatActivity implements HiBaseActionInterface {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
