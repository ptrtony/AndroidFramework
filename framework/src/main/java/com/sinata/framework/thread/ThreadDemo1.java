package com.sinata.framework.thread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 28/6/2021
 */
class ThreadDemo1 {
    static class LooperThread extends Thread{
        private Looper looper;
        public Looper getLooper(){
            synchronized (this){
                if (looper == null && isAlive()){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            return looper;
        }

        @Override
        public void run() {
            Looper.prepare();
            synchronized (this){
                looper = Looper.myLooper();
                notify();
            }
            Looper.loop();

        }
    }


    public static void main(String[] args) {
        LooperThread looperThread = new LooperThread();
        looperThread.start();

        Handler handler = new Handler(looperThread.getLooper()){
            @Override
            public void handleMessage(@NonNull Message msg) {
                super.handleMessage(msg);
                switch (msg.what){

                }
            }
        };

//        handler.sendEmptyMessage()
    }


}
