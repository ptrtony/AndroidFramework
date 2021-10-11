package com.sinata.framework.hi_arch.scene3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sinata.framework.coroutine.User;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:成都博智维讯信息技术股份有限公司
 *
 * @author jingqiang.cheng
 * @date 2021/10/10
 */
public class HomeViewModel extends ViewModel {
    public LiveData<User> getUserData(){
        MutableLiveData<User> userLiveData = new MutableLiveData<>();
        User user = new User("ptrtony","成都市");
        userLiveData.setValue(user);
        return userLiveData;
    }
}
