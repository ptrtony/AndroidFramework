package com.sinata.framework.design.observer;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/11/2
 */
public class ZhaoSi implements Observer {
    @Override
    public void update(String context) {
        System.out.println("ZhaoSi:" + "韩非子现在正在做 " + context);
    }
}
