package com.sinata.framework.service;

import android.app.IntentService;
import android.content.Intent;

import androidx.annotation.Nullable;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2022/3/12
 */
public class IntentServiceDemo extends IntentService {
   /**
    * Creates an IntentService.  Invoked by your subclass's constructor.
    *
    * @param name Used to name the worker thread, important only for debugging.
    */
   public IntentServiceDemo(String name) {
      super(name);
   }

   @Override
   protected void onHandleIntent(@Nullable Intent intent) {
      //做耗时操作的地方
   }

   @Override
   public void onStart(@Nullable Intent intent, int startId) {
      super.onStart(intent, startId);
   }


   @Override
   public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
      return super.onStartCommand(intent, flags, startId);
   }


   @Override
   public void onDestroy() {
      super.onDestroy();
   }
}
