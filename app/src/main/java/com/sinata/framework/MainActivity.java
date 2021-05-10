package com.sinata.framework;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.activity.HiBannerActivity;
import com.sinata.framework.activity.HiRefreshTopActivity;
import com.sinata.framework.activity.HiTabBottomActivity;
import com.sinata.framework.activity.HiTabTopDemoActivity;
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
        findViewById(R.id.btn_bottom).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });
    }


    public void onClickTabBottom(View view){
        startActivity(new Intent(this, HiTabBottomActivity.class));
    }

    public void onClickTabTop(View view){
        startActivity(new Intent(this, HiTabTopDemoActivity.class));
    }

    public void onRefreshClick(View view){
        startActivity(new Intent(this, HiRefreshTopActivity.class));
    }

    public void onBannerClick(View view){
        startActivity(new Intent(this, HiBannerActivity.class));
    }




}
