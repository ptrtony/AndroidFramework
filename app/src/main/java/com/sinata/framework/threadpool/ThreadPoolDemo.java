package com.sinata.framework.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/8
 */
class ThreadPoolDemo {
    public void singleThreadPoolDemo(){
        ExecutorService singleCacheExecutors = Executors.newSingleThreadExecutor();
        /**
         * 线程池
         */
        ExecutorService executorService = Executors.newCachedThreadPool();
        executorService.execute(new Runnable() {
            @Override
            public void run() {

            }
        });
//        ExecutorService executorService1 = Executors.newFixedThreadPool();
//        ScheduledExecutorService executorService2 = Executors.newScheduledThreadPool();
    }
}
