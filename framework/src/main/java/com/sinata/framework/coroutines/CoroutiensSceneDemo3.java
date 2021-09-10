package com.sinata.framework.coroutines;

import java.util.concurrent.CountDownLatch;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 27/6/2021 并发
 */
public class CoroutiensSceneDemo3 {

    public static void main(String[] args) {
        CountDownLatch countDownLatch = new CountDownLatch(5);
        for (int i = 0; i < 5; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(Thread.currentThread().getName() + "准备好了");
                    countDownLatch.countDown();
                }
            }).start();
        }
        try {
            countDownLatch.await();
            System.out.println("所有人都准备好了，准备发车");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
