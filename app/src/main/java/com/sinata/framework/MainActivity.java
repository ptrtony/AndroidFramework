package com.sinata.framework;

import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.logic.MainActivityLogic;

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
        mainActivityLogic = new MainActivityLogic(this,savedInstanceState);
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState, @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
//        mainActivityLogic.onSaveInstanceState(outState);
    }


}
