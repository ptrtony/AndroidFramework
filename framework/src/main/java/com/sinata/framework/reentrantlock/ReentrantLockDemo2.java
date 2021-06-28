package com.sinata.framework.reentrantlock;

import java.util.Random;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description: work1 序列号偶数去搬砖  work2 序列号为奇数去搬砖
 * Copyright:Copyright(c)2021
 *
 * @author jingqiang.cheng
 * @date 26/6/2021
 */
public class ReentrantLockDemo2 {

    public static class ReentrantLockTask {
        Condition work1Condition, work2Condition;
        ReentrantLock lock = new ReentrantLock(true);
        volatile int flag = 0;

        public ReentrantLockTask() {
            work1Condition = lock.newCondition();
            work2Condition = lock.newCondition();
        }

        public void work1() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 == 0) {
                    System.out.println("Work1无砖可搬，进入等待状态");
                    work1Condition.await();
                }
                System.out.println("Work1开始搬砖，搬砖的序列号为:" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void work2() {
            try {
                lock.lock();
                if (flag == 0 || flag % 2 != 0) {
                    System.out.println("Work2无砖可搬，进入等待状态");
                    work2Condition.await();
                }
                System.out.println("Work2开始搬砖，搬砖的序列号为:" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void boss() {
            try {
                lock.lock();
                flag = new Random().nextInt(100);
                if (flag % 2 == 0) {
                    work2Condition.signal();
                    System.out.println("boss 生产出来的砖让wor2去搬砖：" + flag);
                } else {
                    work1Condition.signal();
                    System.out.println("boss 生产出来的砖让wor1去搬砖：" + flag);
                }
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask reentrantLockTask = new ReentrantLockTask();
        new Thread(() -> {
            while (true) {
                reentrantLockTask.work1();
            }
        }).start();

        new Thread(() -> {
            while (true) {
                reentrantLockTask.work2();
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            reentrantLockTask.boss();
        }
    }
}
