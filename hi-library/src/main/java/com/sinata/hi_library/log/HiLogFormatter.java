package com.sinata.hi_library.log;

/**
 * @author cjq
 * @Date 6/4/2021
 * @Time 10:04 PM
 * @Describe:
 */
public interface HiLogFormatter<T> {
    String format(T date);
}
