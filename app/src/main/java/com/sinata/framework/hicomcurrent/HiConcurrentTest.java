package com.sinata.framework.hicomcurrent;

import android.os.AsyncTask;
import android.util.Log;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 5/6/2021
 */
public class HiConcurrentTest {

    private static final String TAG = "HiConcurrentTest";


    class MyAsyncTask extends AsyncTask<String,Integer,String> {

        @Override
        protected String doInBackground(String... params) {
            for (int i = 0; i < 10; i++) {
                publishProgress(i * 10);
            }
            return params[0];
        }

        @Override
        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);
            Log.d(TAG,"onProgressUpdate:" + progress);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            Log.d(TAG,"onPostExecute:" + result);
        }
    }

    public void test(){

        //使用于需要知道执行任务进度并更新UI的场景
        MyAsyncTask myAsyncTask = new MyAsyncTask();
        myAsyncTask.execute("execute my asynctask");

        //以这种方式提交的任务，所有任务串行执行，即先来后到，但是如果其中有一条任务睡眠了，或则执行时间过长，后面的任务都将被阻塞
        AsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"run:AsyncTask execute" );
            }
        });

        //适用于并发任务执行
        AsyncTask.THREAD_POOL_EXECUTOR.execute(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG,"run:THREAD_POOL_EXECUTOR AsyncTask execute" );
            }
        });
    }
}
