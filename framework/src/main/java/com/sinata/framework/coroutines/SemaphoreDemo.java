package com.sinata.framework.coroutines;

import java.util.Random;
import java.util.concurrent.Semaphore;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 27/6/2021
 */
public class SemaphoreDemo {
    public static void main(String[] args) {
        //许可证
        final Semaphore semaphore = new Semaphore(3);
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String name = Thread.currentThread().getName();
                    try {
                        semaphore.acquire();
                        System.out.println(name + "获取了许可证");
                        Thread.sleep(new Random().nextInt(5000));
                        semaphore.release();
                        System.out.println(name + "归还了许可证");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }).start();
        }
    }
}
