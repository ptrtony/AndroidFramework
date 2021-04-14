package com.sinata.hi_library.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * @author cjq
 * @Date 7/4/2021
 * @Time 9:27 PM
 * @Describe:
 */
public class HiLogMo {
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
    public long timeMillis;
    public int level;
    public String tag;
    public String log;

    public HiLogMo(long timeMillis, int level, String tag, String log) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.log = log;
    }

    public String flattenedLog(){
        return getFlattened() + "\n" + log;
    }

    public String getFlattened(){
        return format(timeMillis) +'|'+ level + '|' + tag + "|:";
    }

    private String format(long timeMillis) {
        return sdf.format(timeMillis);
    }


}
