package com.sinata.framework.arouter;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.android.arouter.facade.annotation.Route;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 3/6/2021
 */


@Route(path = "/profile/detail",extras = ARouterFlag.FLAG_LOGIN | ARouterFlag.FLAG_VIP)
public class ProfileDetailActivity extends AppCompatActivity {

}
