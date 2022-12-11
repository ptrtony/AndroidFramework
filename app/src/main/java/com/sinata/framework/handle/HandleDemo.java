package com.sinata.framework.handle;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/3
 */
public class HandleDemo {
    private Handler handler = new Handler(Looper.getMainLooper()){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
        }
    };
    public void handle(){
        Message message = Message.obtain();
        handler.sendMessage(message);
        new Thread(new Runnable() {
            @Override
            public void run() {
//                handler.sendEmptyMessage()
                Looper.prepare();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //更新UI
                    }
                });

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        handler.sendEmptyMessage(1);
                    }
                },100);

                Looper.loop();
            }
        }).start();
    }
}
