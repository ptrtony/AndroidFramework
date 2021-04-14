package com.sinata.hi_library.log;

/**
 * @author cjq
 * @Date 4/4/2021
 * @Time 7:09 PM
 * @Describe:
 */
public abstract class HiLogConfig {
    static int MAX_LENGTH = 512;
    static HiStackTraceFormatter HI_STACK_TRACE_FORMATTER = new HiStackTraceFormatter();
    static HiTreadFormatter HI_TREAD_FORMATTER = new HiTreadFormatter();

    public JsonParser injectJsonParser() {
        return null;
    }

    public String getGlobalConfigTag() {
        return "HiLog";
    }

    public boolean enable() {
        return true;
    }

    public boolean includeThread() {
        return false;
    }


    public int stackTraceDepth() {
        return 5;
    }

    public HiLogPrinter[] printers() {
        return null;
    }

    public interface JsonParser {
        String toJson(Object src);
    }
}
