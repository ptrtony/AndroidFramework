package com.sinata.framework.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * //公平锁：进入阻塞的线程，均有机会执行程序
 * //非公平锁：允许线程插队，避免每个程序都进入阻塞，再唤醒，性能高，因为存在线程插队的情况，可能会导致线程饿死的情况
 * 使用场景:
 * 公平锁：交易
 * 非公平锁：synchronized
 * @author jingqiang.cheng
 * @date 26/6/2021
 */
public class ReentrantLockDemo1 {

    public static class ReentrantLockTask{

        ReentrantLock lock = new ReentrantLock(true);
        public void print(){
            String name = Thread.currentThread().getName();
            try{
                lock.lock();
                System.out.println(name + "打印第一张");
                Thread.sleep(300);
                lock.unlock();
                Thread.sleep(300);
                lock.lock();
                System.out.println(name + "打印第二张");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        public static void main(String[] args) {
            ReentrantLockTask reentrantLockTask = new ReentrantLockTask();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    reentrantLockTask.print();
                }
            };
            for (int i = 0; i < 10; i++) {
                new Thread(runnable).start();
            }
        }
    }

}
