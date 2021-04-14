package com.sinata.framework.log;

/**
 * @author cjq
 * @Date 7/4/2021
 * @Time 12:07 AM
 * @Describe:
 */
public class HiStackTraceUtil {


    public static StackTraceElement[] getCroppedRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage, int maxDepth) {
        return copyStackTrace(getRealStackTrace(stackTrace, ignorePackage), maxDepth);
    }

    /**
     * 获取除忽略包以外的堆栈信息
     *
     * @param stackTrace
     * @param ignorePackage
     * @return
     */
    private static StackTraceElement[] getRealStackTrace(StackTraceElement[] stackTrace, String ignorePackage) {
        int ignoreDepth = 0;
        int allDepth = stackTrace.length;
        String className;
        for (int len = allDepth - 1; len > 0; len--) {
            className = stackTrace[len].getClassName();
            if (ignorePackage != null && className.startsWith(ignorePackage)) {
                ignoreDepth += 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStackTrace = new StackTraceElement[realDepth];
        System.arraycopy(stackTrace, 0, realStackTrace, 0, realDepth);
        return realStackTrace;
    }

    /**
     * 剪裁堆栈信息
     *
     * @param callStack
     * @param maxDepth
     * @return
     */
    private static StackTraceElement[] copyStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = callStack.length;
        if (realDepth > 0) {
            realDepth = Math.min(realDepth, maxDepth);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
