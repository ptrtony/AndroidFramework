package com.sinata.framework.arithmatic;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

/**
 * @author cjq
 * @Date 11/5/2021
 * @Time 11:34 AM
 * @Describe:
 */
public class ArithmaticActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new TextView(this));
//        Solution.removeElement(new int[]{1,2,2,2,4},2);
    }
}
