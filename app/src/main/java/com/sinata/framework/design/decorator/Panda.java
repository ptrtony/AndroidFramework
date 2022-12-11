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
public class Panda implements Animal{
    @Override
    public void eat() {
        System.out.println("什么都没有，不知道吃什么");
    }
}
