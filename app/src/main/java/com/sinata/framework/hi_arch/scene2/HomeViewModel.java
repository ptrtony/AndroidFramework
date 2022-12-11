package com.sinata.framework.hi_arch.scene2;

import androidx.databinding.ObservableField;
import androidx.lifecycle.ViewModel;

import com.sinata.framework.coroutine.User;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/10/10
 */
public class HomeViewModel extends ViewModel {

    public ObservableField<User> mInfo  = new ObservableField<>();

    public void getUserInfo(){
        User user = new User("ptytony","成都市双流区");
        mInfo.set(user);
    }
}
