package com.sinata.framework.design.observer;

import java.util.ArrayList;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/11/2
 */
public class HanFeiZi implements IHanFeiZi,Observable{


    private ArrayList<Observer> observers = new ArrayList<>();

    @Override
    public void haveBreastFast() {
        this.notifyObservers("韩非子开始吃早餐了......");
    }

    @Override
    public void haveFun() {
        this.notifyObservers("韩非子最大的爱好是吃麻辣烫......");
    }

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(String context) {
        for (int i = 0; i < observers.size(); i++) {
            observers.get(i).update(context);
        }
    }
}
