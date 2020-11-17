package cn.djzhao.library.log;

import android.util.Log;

import androidx.annotation.NonNull;

/**
 * log系统
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class DJLog {
    public static void v(Object... contents) {
        log(DJLogType.V, contents);
    }

    public static void vt(String tag, Object... contents) {
        log(DJLogType.V, tag, contents);
    }

    public static void d(Object... contents) {
        log(DJLogType.D, contents);
    }

    public static void dt(String tag, Object... contents) {
        log(DJLogType.D, tag, contents);
    }

    public static void i(Object... contents) {
        log(DJLogType.I, contents);
    }

    public static void it(String tag, Object... contents) {
        log(DJLogType.I, tag, contents);
    }

    public static void w(Object... contents) {
        log(DJLogType.W, contents);
    }

    public static void wt(String tag, Object... contents) {
        log(DJLogType.W, tag, contents);
    }

    public static void e(Object... contents) {
        log(DJLogType.E, contents);
    }

    public static void et(String tag, Object... contents) {
        log(DJLogType.E, tag, contents);
    }

    public static void a(Object... contents) {
        log(DJLogType.A, contents);
    }

    public static void at(String tag, Object... contents) {
        log(DJLogType.A, tag, contents);
    }

    public static void log(@DJLogType.TYPE int type, Object... contents) {
        log(type, DJLogManger.getInstance().getConfig().getGlobalTag(), contents);
    }

    public static void log(@DJLogType.TYPE int type, @NonNull String tag, Object... contents) {
        log(DJLogManger.getInstance().getConfig(), type, tag, contents);
    }

    public static void log(@NonNull DJLogConfig config, @DJLogType.TYPE int type, @NonNull String tag, Object... contents) {
        if (!config.enable()) {
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        String body = parseBody(contents);
        stringBuilder.append(body);
        Log.println(type, tag, stringBuilder.toString());
    }

    public static String parseBody(@NonNull Object[] contents) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Object content : contents) {
            stringBuilder.append(content.toString()).append(":");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
