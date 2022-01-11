package com.sinata.framework;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

import com.sinata.common.ui.component.HiBaseActivity;
import com.sinata.framework.activity.HiBannerActivity;
import com.sinata.framework.activity.HiRefreshTopActivity;
import com.sinata.framework.activity.HiTabBottomActivity;
import com.sinata.framework.activity.HiTabTopDemoActivity;
import com.sinata.framework.biz.account.LoginActivity;
import com.sinata.framework.camera.CameraDemoActivity;
import com.sinata.framework.logic.MainActivityLogic;
import com.sinata.framework.navigator.ui.NavigationDemoActivity;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:55 PM
 * @Describe:
 */
public class MainActivity extends HiBaseActivity implements MainActivityLogic.ActivityProvider {

//    private MainActivityLogic mainActivityLogic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.btn_bottom).getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {

            }
        });


        double d =  Double.parseDouble("1");
//        startActivity(new Intent(this, LoginActivity.class));

////
////        //显示意图
//        startActivity(new Intent(MainActivity.this, HomePageFragment.class));
////
////        //隐式
//        startActivity(new Intent("home:fhsjfjsa"));

//
//        ApiFactory.INSTANCE.create(AccountApi.class).listCitys("imooc").enqueue(new HiCallback<JsonObject>() {
//            @Override
//            public void onSuccess(@NotNull HiResponse<JsonObject> data) {
//                String msg = data.getMsg();
//            }
//
//            @Override
//            public void onFailed(@NotNull Throwable throwable) {
//                throwable.printStackTrace();
//            }
//        });
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

    public void onNavigationClick(View view){
        startActivity(new Intent(this, NavigationDemoActivity.class));
    }



    public int balancedStringSplit(String s) {
        int num = 0;
        int n = 0;
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == 'R') {
                n++;
            }

            if (s.charAt(i) == 'L') {
                n--;
            }

            if (n == 0) {
                num++;
            }
        }
        return num;
    }

}
