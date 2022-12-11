package com.sinata.framework.service;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import androidx.annotation.Nullable;
import org.jetbrains.annotations.NotNull;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/6
 */
class ServiceDemoActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = new Intent(this, ServiceDemo.class);
        startService(intent);

        /**
         *      {@link #BIND_AUTO_CREATE}, {@link #BIND_DEBUG_UNBIND},
         *      *          {@link #BIND_NOT_FOREGROUND}, {@link #BIND_ABOVE_CLIENT},
         *      *          {@link #BIND_ALLOW_OOM_MANAGEMENT}, {@link #BIND_WAIVE_PRIORITY}.
         *      *          {@link #BIND_IMPORTANT}, or
         *      *          {@link #BIND_ADJUST_WITH_ACTIVITY}.
         */
        bindService(intent, connection(),BIND_NOT_FOREGROUND);
    }

    @NotNull
    private ServiceConnection connection() {
        return new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {

            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection());
    }
}
