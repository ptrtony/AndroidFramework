package com.sinata.hi_library.log;

import android.util.Log;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cjq
 * @Date 4/4/2021
 * @Time 6:46 PM
 * @Describe:
 */
public class HiLogType {

    @IntDef({HiLogType.V, HiLogType.D, HiLogType.I, HiLogType.W, HiLogType.E, HiLogType.A})

    @Retention(RetentionPolicy.SOURCE)
    public @interface TYPE {

    }

    public final static int V = Log.VERBOSE;
    public final static int D = Log.DEBUG;
    public final static int I = Log.INFO;
    public final static int W = Log.WARN;
    public final static int E = Log.ERROR;
    public final static int A = Log.ASSERT;
}
