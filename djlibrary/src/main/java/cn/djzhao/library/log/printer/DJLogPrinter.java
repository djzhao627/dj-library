package cn.djzhao.library.log.printer;

import androidx.annotation.NonNull;

import cn.djzhao.library.log.DJLogConfig;

/**
 * 日志打印接口
 *
 * @author djzhao
 * @date 20/12/31
 */
public interface DJLogPrinter {
    void print(@NonNull DJLogConfig config, int level, String tag, @NonNull String message);
}
