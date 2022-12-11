package com.sinata.framework.hi_arch.scene1;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/10/8
 */
public class BasePresenter<IView extends BaseView> {

    IView view;
    public void attach(IView view){
        this.view = view;
    }

    public void detach(){
        this.view = null;
    }
}
