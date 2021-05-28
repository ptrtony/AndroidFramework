package com.sinata.framework.lazy_fragment.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.sinata.framework.R;
import com.sinata.hi_library.log.HiLog;

/**
 * @author cjq
 * @Date 11/5/2021
 * @Time 8:58 PM
 * @Describe:
 */
public class FirstFragment extends Fragment {

    private static final String TAG = "FirstFragment";

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"FirstFragment ------ onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_first,container,false);
        }
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"FirstFragment ------ onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"FirstFragment ------ onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"FirstFragment ------ onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"FirstFragment ------ onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"FirstFragment ------ onDestroy");
    }
}
