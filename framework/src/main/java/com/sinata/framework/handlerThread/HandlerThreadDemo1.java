package com.sinata.framework.handlerThread;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 27/6/2021
 */


public class HandlerThreadDemo1 {
    public static final int MSG_WHAT_FLAG_1 = 1;
    //适用于主线程需要的子线程通讯的场景
    //应用于持续性任务，适用于轮询
    public void handlerThreadMethod(){
        HandlerThread thread = new HandlerThread("corcureent-thread");
        thread.start();
        MyHandler handler = new MyHandler(thread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);

            }
        };
        handler.sendEmptyMessage(MSG_WHAT_FLAG_1);
        thread.quitSafely();

    }

    //定义成静态，防止内存泄漏
    public static class MyHandler extends Handler{
        public MyHandler(Looper looper){
            super(looper);
        }


        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);

        }
    }

}
