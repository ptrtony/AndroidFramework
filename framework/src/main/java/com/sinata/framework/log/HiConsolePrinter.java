package com.sinata.framework.log;

import android.util.Log;

import org.jetbrains.annotations.NotNull;

import static com.sinata.framework.log.HiLogConfig.MAX_LENGTH;

/**
 * @author cjq
 * @Date 6/4/2021
 * @Time 10:01 PM
 * @Describe:
 */
public class HiConsolePrinter implements HiLogPrinter {

    @Override
    public void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString) {
        int len = printString.length();
        int countOfSub = len / MAX_LENGTH;
        if (countOfSub > 0) {
            int index = 0;
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, printString.substring(index, index + MAX_LENGTH));
                index += MAX_LENGTH;
            }
            if (index != len) {
                Log.println(level, tag, printString.substring(index, len));
            }
        }else{
            Log.println(level, tag, printString);
        }
    }
}
