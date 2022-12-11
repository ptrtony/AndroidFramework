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
public class ObservableClient {
    public static void main(String[] args) {
        HanFeiZi hanFeiZi  = new HanFeiZi();
        Observer lisi = new LiSi();
        Observer wangwu = new WangWu();
        Observer zhaoSi = new ZhaoSi();
        hanFeiZi.addObserver(lisi);
        hanFeiZi.addObserver(wangwu);
        hanFeiZi.addObserver(zhaoSi);
        hanFeiZi.haveBreastFast();
    }
}
