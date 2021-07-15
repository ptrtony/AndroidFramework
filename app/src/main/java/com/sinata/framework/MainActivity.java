package com.sinata.framework;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.os.strictmode.IntentReceiverLeakedViolation;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.gson.JsonObject;
import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.activity.HiBannerActivity;
import com.sinata.framework.activity.HiRefreshTopActivity;
import com.sinata.framework.activity.HiTabBottomActivity;
import com.sinata.framework.activity.HiTabTopDemoActivity;
import com.sinata.framework.camera.CameraDemoActivity;
import com.sinata.framework.fragment.HomePageFragment;
import com.sinata.framework.http.ApiFactory;
import com.sinata.framework.http.api.TestApi;
import com.sinata.framework.logic.MainActivityLogic;
import com.sinata.hi_library.restful.HiCallback;
import com.sinata.hi_library.restful.HiResponse;

import org.jetbrains.annotations.NotNull;

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

////
////        //显示意图
//        startActivity(new Intent(MainActivity.this, HomePageFragment.class));
////
////        //隐式
//        startActivity(new Intent("home:fhsjfjsa"));


        ApiFactory.INSTANCE.create(TestApi.class).listCitys("imooc").enqueue(new HiCallback<JsonObject>() {
            @Override
            public void onSuccess(@NotNull HiResponse<JsonObject> data) {

            }

            @Override
            public void onFailed(@NotNull Throwable throwable) {

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

    public void onCameraClick(View view){
        startActivity(new Intent(this, CameraDemoActivity.class));
    }


}
