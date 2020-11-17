package cn.djzhao.library.log;

import androidx.annotation.NonNull;

/**
 * DJLog管理类
 */
public class DJLogManger {
    private DJLogConfig config;
    private static DJLogManger instance;

    private DJLogManger(DJLogConfig config) {
        this.config = config;
    }

    public static DJLogManger getInstance() {
        return instance;
    }

    public static void init(@NonNull DJLogConfig config) {
        if (instance == null) {
            instance = new DJLogManger(config);
        }
    }

    public DJLogConfig getConfig() {
        return config;
    }
}
