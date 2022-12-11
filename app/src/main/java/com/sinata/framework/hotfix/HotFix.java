package com.sinata.framework.hotfix;

import android.content.Context;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Title:
 * Description:
 * Copyright:Copyright(c)2021
 * Company:
 *
 * @author jingqiang.cheng
 * @date 2021/9/25
 */
public class HotFix {

    public static void fix(Context context, File pathDexFile) throws IllegalAccessException, InvocationTargetException {
        ClassLoader classLoader = context.getClassLoader();
        Field pathListField = findField(classLoader, "pathList");
        Object pathList = pathListField.get(classLoader);

        List<File> files = Arrays.asList(pathDexFile);
        Object[] patchDexElements = makeDexElements(pathList, files, classLoader);
        combineDexElements(pathList,patchDexElements);
    }

    private static void combineDexElements(Object pathList, Object[] patchDexElements) throws IllegalAccessException {
        Field dexElementsField = findField(pathList, "dexElements");
        Object[] original = (Object[]) dexElementsField.get(pathList);
        Object combined = Array.newInstance(original.getClass().getComponentType(),patchDexElements.length + original.length);
        System.arraycopy(patchDexElements,0,combined,0,patchDexElements.length);
        System.arraycopy(patchDexElements,0,combined,patchDexElements.length,original.length);
        dexElementsField.set(pathList,combined);
    }

    private static Object[] makeDexElements(Object pathList, List<File> files, ClassLoader classLoader) throws InvocationTargetException, IllegalAccessException {
        Method method = findMethod(pathList, "makeDexElements", List.class, File.class, List.class, ClassLoader.class);
        if (method != null) {
            ArrayList<IOException> ioExceptions = new ArrayList<>();
            return (Object[]) method.invoke(pathList, files, null, ioExceptions, classLoader);
        }
        return null;
    }

    private static Method findMethod(Object instance, String methodName, Class<?>... parameterTypes) {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
                if (!method.isAccessible()) {
                    method.setAccessible(true);
                }
                return method;
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static Field findField(Object instance, String filedName) {
        for (Class<?> clazz = instance.getClass(); clazz != null; clazz = clazz.getSuperclass()) {
            try {
                Field field = clazz.getDeclaredField(filedName);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                return field;
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }

        return null;
    }
}
