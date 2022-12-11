package com.sinata.framework.design.singleton;

/**
 * Title: 静态内部类的单例实现
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/10/7
 */
public class Singleton4Java {
    private static class SingletonProvider{
        private static Singleton4Java instance  = new Singleton4Java();
    }


    private Singleton4Java(){

    }

    public static Singleton4Java getInstance(){
        return SingletonProvider.instance;
    }

}
