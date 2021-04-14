package com.sinata.framework.log;

/**
 * @author cjq
 * @Date 6/4/2021
 * @Time 10:07 PM
 * @Describe: 线程
 */
public class HiTreadFormatter implements HiLogFormatter<Thread>{
    @Override
    public String format(Thread date) {
        return "Thread:" + date.getName();
    }
}
