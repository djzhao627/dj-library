package cn.djzhao.library.log;

import androidx.annotation.NonNull;

/**
 * 日志打印接口
 *
 * @author djzhao
 * @date 20/12/31
 */
public interface DJLogPrinter {
    void print(@NonNull DJLogConfig config, int level, String tag, @NonNull String message);
}
