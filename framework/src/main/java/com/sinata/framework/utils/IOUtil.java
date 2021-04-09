package com.sinata.framework.utils;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author cjq
 * @Date 9/4/2021
 * @Time 10:59 PM
 * @Describe:
 */
public class IOUtil {
    public static void closeIOStream(Closeable closeable) throws IOException {
        if (closeable != null){
            closeable.close();
        }
    }
}
