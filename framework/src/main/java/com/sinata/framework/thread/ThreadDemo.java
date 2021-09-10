package com.sinata.framework.thread;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 28/6/2021
 */
public class ThreadDemo {

    final static Object object = new Object();

    static class Runnable1 implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                try {
                    System.out.println("runnable1 开始");
                    object.wait();
                    System.out.println("runnable1 结束");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    static class Runnable2 implements Runnable{

        @Override
        public void run() {
            synchronized (object){
                System.out.println("runnable2 开始");
                 object.notify();
                System.out.println("runnable2 结束");
            }
        }
    }


    public static void main(String[] args) {
        new Thread(new Runnable1()).start();
        new Thread(new Runnable2()).start();
    }
}
