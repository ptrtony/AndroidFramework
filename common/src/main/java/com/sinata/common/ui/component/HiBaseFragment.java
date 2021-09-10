package com.sinata.common.ui.component;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

/**
 * @author cjq
 * @Date 14/4/2021
 * @Time 9:10 PM
 * @Describe:
 */
public abstract class HiBaseFragment extends Fragment {
    protected View layoutView;

    @LayoutRes
    public abstract int getLayoutId();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        layoutView = inflater.inflate(getLayoutId(), container,false);
        return layoutView;
    }


    /**
     * 判断当前的页面是否被销毁
     * @return
     */
    public boolean isAlive(){
        if (isRemoving() || isDetached() || getActivity() == null) {
            return false;
        }
        return true;
    }

}

