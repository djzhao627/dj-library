package cn.djzhao.library.log.formatter;

/**
 * 堆栈信息格式化器
 *
 * @author djzhao
 * @date 20/12/31
 */
public class DJStackTraceFormatter implements DJLogFormatter<StackTraceElement[]> {
    @Override
    public String format(StackTraceElement[] stackTrace) {
        if (stackTrace == null || stackTrace.length == 0) {
            return null;
        } else if (stackTrace.length == 1) {
            return "\t- " + stackTrace[0].toString();
        } else {
            StringBuilder appender = new StringBuilder(128);
            for (int i = 0, len = stackTrace.length; i < len; i++) {
                if (i == 0) {
                    appender.append("StackTrace: \n");
                }
                if (i != len - 1) {
                    appender.append("\t├ ")
                            .append(stackTrace[i].toString())
                            .append("\n");
                } else {
                    appender.append("\t└ ")
                            .append(stackTrace[i].toString());
                }
            }
            return appender.toString();
        }
    }
}
