package com.sinata.framework.reentrantlock;

import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Title:
 * Description:
 * 共享锁：所有线程均可同时获取，并发高，比如在线文档查看
 * 排他锁：同一时刻只有一个线程有权限修改资源，比如在线文档编辑
 * Copyright:Copyright(c)2021
 *
 * readLock共享锁
 * writeLock 排他锁
 * @author jingqiang.cheng
 * @date 26/6/2021
 */
public class ReentrantReadWriteLockDemo {

    public static class ReentrantReadWriteLockTask{

        private final ReentrantReadWriteLock.ReadLock readLock;
        private final ReentrantReadWriteLock.WriteLock writeLock;
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

        public ReentrantReadWriteLockTask(){
            readLock = lock.readLock();
            writeLock = lock.writeLock();
        }

        public void read(){
            String name = Thread.currentThread().getName();
            try{
                readLock.lock();
                System.out.println("线程" + name + ":正在读取数据...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readLock.unlock();
                System.out.println("线程" + name + ":正在释放锁");
            }
        }

        public void write(){
            String name = Thread.currentThread().getName();
            try{
                writeLock.lock();
                System.out.println("线程" + name +":正在写入数据...");
            }finally {
                writeLock.unlock();
                System.out.println("线程" + name + ":正在释放锁");
            }
        }
    }


    public static void main(String[] args) {
        ReentrantReadWriteLockTask task = new ReentrantReadWriteLockTask();
        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.read();
                }
            }).start();
        }


        for (int i = 0; i < 10; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    task.write();
                }
            }).start();
        }
    }
}
