package cn.djzhao.library.log;

/**
 * 堆栈信息工具类
 *
 * @author djzhao
 * @date 20/12/31
 */
public class DJStackTraceUtil {

    /**
     * 堆栈信息休整
     *
     * @param callStack         目标堆栈
     * @param ignorePackageName 忽略的包名
     * @param maxDepth          最大深度
     * @return 新堆栈
     */
    public static StackTraceElement[] trimStackTrace(StackTraceElement[] callStack, String ignorePackageName, int maxDepth) {
        return cropStackTrace(getUsefulStackTrace(callStack, ignorePackageName), maxDepth);
    }

    /**
     * 根据报名获取有效的堆栈信息
     *
     * @param callStack         目标堆栈
     * @param ignorePackageName 忽略的包名
     * @return 新堆栈
     */
    private static StackTraceElement[] getUsefulStackTrace(StackTraceElement[] callStack, String ignorePackageName) {
        if (ignorePackageName == null) {
            return callStack;
        }
        int ignoreDepth = 0;
        int allDepth = callStack.length;
        String className;
        for (int i = allDepth - 1; i >= 0; i--) {
            className = callStack[i].getClassName();
            if (className.startsWith(ignorePackageName)) {
                ignoreDepth = i + 1;
                break;
            }
        }
        int realDepth = allDepth - ignoreDepth;
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, ignoreDepth, realStack, 0, realDepth);
        return realStack;
    }

    /**
     * 堆栈深度裁剪
     *
     * @param callStack 目标堆栈
     * @param maxDepth  最大深度
     * @return 新堆栈
     */
    private static StackTraceElement[] cropStackTrace(StackTraceElement[] callStack, int maxDepth) {
        int realDepth = 0;
        if (maxDepth > 0) {
            realDepth = Math.min(maxDepth, callStack.length);
        }
        StackTraceElement[] realStack = new StackTraceElement[realDepth];
        System.arraycopy(callStack, 0, realStack, 0, realDepth);
        return realStack;
    }
}
