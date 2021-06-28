package com.sinata.framework.reentrantlock;

import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 26/6/2021
 */
public class ReentrantLockDemo {

    public static class ReentrantLockTask {
        ReentrantLock lock = new ReentrantLock();
        public void buyTicket() {
            String name = Thread.currentThread().getName();
            try {
                lock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(1000);
                System.out.println(name+":买好了");
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        //可重入锁是指在同一个线程在不释放锁的的前提下，去获取锁
        public void buyTicket1(){
            ReentrantLock lock = new ReentrantLock();
            String name = Thread.currentThread().getName();
            try{
                lock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(300);
                System.out.println(name + ":买好了");
                buyTicket1();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


    }


    public static void main(String[] args) {
        ReentrantLockTask reentrantLockTask = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                reentrantLockTask.buyTicket1();
            }
        };
        for (int i = 0; i < 20; i++) {
           new Thread(runnable).start();
        }
    }
}
