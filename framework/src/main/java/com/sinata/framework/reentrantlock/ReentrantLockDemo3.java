package com.sinata.framework.reentrantlock;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 28/6/2021
 */
public class ReentrantLockDemo3 {

    static class ReentrantLockDemo3Task{
        ReentrantLock lock = new ReentrantLock();
        Condition customerACondition,customerBCondition;
//        Condition condition;
        AtomicInteger breadCount;
        public ReentrantLockDemo3Task(){
//            Condition condition = lock.newCondition();
            breadCount = new AtomicInteger();
            customerACondition = lock.newCondition();
            customerBCondition = lock.newCondition();
        }
        public void customerA(){
            try{
                String name = Thread.currentThread().getName();
                lock.lock();
                if (breadCount.get() == 0 || breadCount.get() < 0){
                    breadCount.set(0);
                    System.out.println(name + "暂无面包,请等待");
                    customerACondition.await();
                }
                Thread.sleep(2000);
                breadCount.set(breadCount.get() - 1);
                System.out.println(name + "customerA吃了一块面包，当前面包数量：" + breadCount.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void customerB(){
            try{
                String name = Thread.currentThread().getName();
                lock.lock();
                if (breadCount.get() == 0 || breadCount.get() < 0){
                    breadCount.set(0);
                    System.out.println(name + "暂无面包,请等待");
                    customerACondition.await();
                }
                Thread.sleep(3000);
                breadCount.set(breadCount.get() - 1);
                System.out.println(name + "customerB吃了一块面包，当前面包数量：" + breadCount.get());

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }

        public void product(){
            try{
                String name = Thread.currentThread().getName();
                lock.lock();
                Thread.sleep(1000);
                breadCount.set(breadCount.get() + 1);
                System.out.println(name + "生产者生产了一块面包：" + breadCount.get());
                customerACondition.signal();
                customerBCondition.signal();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        ReentrantLockDemo3Task task = new ReentrantLockDemo3Task();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true){
                    task.customerA();
                }
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
               while (true){
                   task.customerB();
               }
            }
        }).start();

        for (int i = 0; i < 10; i++) {
            new Thread(() -> task.product()).start();
        }
    }

}
