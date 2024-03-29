package com.sinata.framework.classloader;

import java.io.File;

import dalvik.system.BaseDexClassLoader;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/12/10
 */
class PathClassLoader extends BaseDexClassLoader {
    public PathClassLoader(String dexPath, File optimizedDirectory, String librarySearchPath, ClassLoader parent) {
        super(dexPath, optimizedDirectory, librarySearchPath, parent);
    }
}
