package cn.djzhao.library.log;

import androidx.annotation.NonNull;

import java.util.Arrays;
import java.util.List;

import cn.djzhao.library.log.printer.DJLogPrinter;
import cn.djzhao.library.log.formatter.DJStackTraceUtil;

/**
 * log系统
 * 1. 打印堆栈信息
 * 2. File输出
 * 3. 模拟控制台
 */
public class DJLog {

    private static final String DJ_LOG_PACKAGE;

    static {
        String className = DJLog.class.getName();
        DJ_LOG_PACKAGE = className.substring(0, className.lastIndexOf('.'));
    }

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
        if (config.includeThread()) {
            stringBuilder.append(DJLogConfig.DJ_THREAD_FORMATTER.format(Thread.currentThread()))
                    .append("\n");
        }
        if (config.stackTraceDepth() > 0) {
            stringBuilder.append(DJLogConfig.DJ_STACK_TRACE_FORMATTER.format(DJStackTraceUtil.trimStackTrace(new Throwable().getStackTrace(), DJ_LOG_PACKAGE, config.stackTraceDepth())))
                    .append("\n");
        }
        List<DJLogPrinter> printers;
        if (config.printers() != null) {
            printers = Arrays.asList(config.printers());
        } else {
            printers = DJLogManger.getInstance().getPrinters();
        }
        if (printers == null) {
            return;
        }
        String body = parseBody(config, contents);
        stringBuilder.append(body);
        for (DJLogPrinter printer : printers) {
            printer.print(config, type, tag, stringBuilder.toString());
        }
    }

    public static String parseBody(DJLogConfig config, @NonNull Object[] contents) {
        StringBuilder stringBuilder = new StringBuilder();
        if (config.injectJsonParser() != null) {
            return config.injectJsonParser().toJson(contents);
        }
        for (Object content : contents) {
            stringBuilder.append(content.toString()).append(":");
        }
        if (stringBuilder.length() > 0) {
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }
}
