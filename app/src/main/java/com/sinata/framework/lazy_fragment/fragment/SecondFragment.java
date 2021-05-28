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

/**
 * @author cjq
 * @Date 11/5/2021
 * @Time 9:05 PM
 * @Describe:
 */
public class SecondFragment extends Fragment {

    private static final String TAG = "SecondFragment";

    View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG,"SecondFragment ------ onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null){
            rootView = inflater.inflate(R.layout.fragment_second,container,false);
        }
        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG,"SecondFragment ------ onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG,"SecondFragment ------ onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG,"SecondFragment ------ onStop");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG,"SecondFragment ------ onPause");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG,"SecondFragment ------ onDestroy");
    }
}
