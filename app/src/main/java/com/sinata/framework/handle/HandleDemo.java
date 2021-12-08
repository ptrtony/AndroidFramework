package com.sinata.framework.handle;

import android.os.Handler;
import android.os.Looper;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/12/3
 */
public class HandleDemo {
    private Handler handler = new Handler(Looper.getMainLooper());
    public void handle(){
        new Thread(new Runnable() {
            @Override
            public void run() {
//                handler.sendEmptyMessage()
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                    }
                },100);
            }
        }).start();
    }
}
