package com.sinata.framework.design.observer;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/11/2
 */
public class LiSi implements Observer {
    @Override
    public void update(String context) {
        System.out.println("LiSi:" + "韩非子现在正在做 " + context);
    }
}
