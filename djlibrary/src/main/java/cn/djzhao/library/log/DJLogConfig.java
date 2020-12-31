package cn.djzhao.library.log;

/**
 * DJLog配置信息
 */
public abstract class DJLogConfig {

    /**
     * 每行最大长度
     */
    static int MAX_LINE_LENGTH = 512;

    static DJThreadFormatter DJ_THREAD_FORMATTER = new DJThreadFormatter();
    static DJStackTraceFormatter DJ_STACK_TRACE_FORMATTER = new DJStackTraceFormatter();

    /**
     * 对象格式化接口
     */
    public interface JsonParser {
        String toJson(Object src);
    }

    /**
     * 对象格式化注入方法
     *
     * @return 注入器
     */
    public JsonParser injectJsonParser() {
        return null;
    }

    /**
     * 获取全局Tag
     *
     * @return Tag
     */
    public String getGlobalTag() {
        return "DJLog";
    }

    /**
     * 获取是否启用
     *
     * @return 是/否
     */
    public boolean enable() {
        return true;
    }

    /**
     * 是否包含线程信息
     *
     * @return 是否
     */
    public boolean includeThread() {
        return false;
    }

    /**
     * 堆栈信息打印深度
     *
     * @return 深度
     */
    public int stackTraceDepth() {
        return 5;
    }

    /**
     * 配置打印器
     *
     * @return 打印器
     */
    public DJLogPrinter[] printers() {
        return null;
    }
}
