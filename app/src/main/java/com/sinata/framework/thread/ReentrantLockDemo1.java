package com.sinata.framework.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 22/6/2021
 */
public class ReentrantLockDemo1 {


    //work 1 奇数 work 2偶数
    static class ReentrantLockTask{
        private Condition work1Condition,work2Condition;
        ReentrantLock lock = new ReentrantLock();
        volatile int flag = 0;//砖的序列号
        public ReentrantLockTask(){
            work1Condition = lock.newCondition();
            work2Condition = lock.newCondition();
        }

        void work1(){
            try{
                lock.lock();
                if (flag == 0 || flag % 2 == 0){
                    System.out.println("work1 无砖可搬,休息会");
                    work1Condition.await();
                }
                System.out.println("work1 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        void work2(){
            try{
                lock.lock();
                if (flag == 0 || flag % 2 != 0){
                    System.out.println("work2 无砖可搬,休息会");
                    work2Condition.await();
                }
                System.out.println("work2 搬到的砖是：" + flag);
                flag = 0;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }


        void boos(){
            try{
                lock.lock();
                flag = (int) (Math.random() * 100);
                if (flag % 2 == 0){
                    work2Condition.signal();
                    System.out.println("生产出来的砖，唤醒工人2去搬砖：" + flag);
                }else{
                    work1Condition.signal();
                    System.out.println("生产出来的砖，唤醒工人1去搬砖：" + flag);
                }
            }finally {
                lock.unlock();
            }
        }



        public static void main(String[] args){
            ReentrantLockTask task = new ReentrantLockTask();
            Runnable runnable = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        task.work1();
                    }
                }
            };


            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        task.work2();
                    }
                }
            };



            Runnable runnable2 = new Runnable() {
                @Override
                public void run() {
                    while (true){
                        task.boos();
                    }
                }
            };
            new Thread(runnable).start();
            new Thread(runnable1).start();
            new Thread(runnable2).start();
        }

    }

}
