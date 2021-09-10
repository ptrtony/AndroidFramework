package com.sinata.framework.arouter;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:company
 *
 * @author jingqiang.cheng
 * @date 2/6/2021
 */
public interface ARouterFlag {

    int FLAG_LOGIN = 0x11;
    int FLAG_AUTHENTICATION = FLAG_LOGIN << 1;
    int FLAG_VIP = FLAG_AUTHENTICATION << 1;
}
