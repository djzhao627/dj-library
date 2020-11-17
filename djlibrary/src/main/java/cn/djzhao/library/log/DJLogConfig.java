package cn.djzhao.library.log;

/**
 * DJLog配置信息
 */
public abstract class DJLogConfig {

    /**
     * 获取全局Tag
     * @return Tag
     */
    public String getGlobalTag() {
        return "DJLog";
    }

    /**
     * 获取是否启用
     * @return 是/否
     */
    public boolean enable() {
        return true;
    }
}
