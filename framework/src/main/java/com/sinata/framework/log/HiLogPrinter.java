package com.sinata.framework.log;

import org.jetbrains.annotations.NotNull;

/**
 * @author cjq
 * @Date 6/4/2021
 * @Time 10:00 PM
 * @Describe:
 */
public interface HiLogPrinter {
    void print(@NotNull HiLogConfig config,int level,String tag,@NotNull String printString);
}
