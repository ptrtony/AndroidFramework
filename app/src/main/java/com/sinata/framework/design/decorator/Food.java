package com.sinata.framework.design.decorator;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/10/7
 */
public abstract class Food implements Animal{
    Animal animal;

    public Food(Animal animal) {
        this.animal = animal;
    }

    @Override
    public void eat() {
        animal.eat();
    }
}
