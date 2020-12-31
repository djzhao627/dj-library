package cn.djzhao.library.log;

import android.util.Log;

import androidx.annotation.NonNull;

import static cn.djzhao.library.log.DJLogConfig.MAX_LINE_LENGTH;

/**
 * 控制台打印器
 *
 * @author djzhao
 * @date 20/12/31
 */
public class DJConsolePrinter implements DJLogPrinter {
    @Override
    public void print(@NonNull DJLogConfig config, int level, String tag, @NonNull String message) {
        int messageLen = message.length();
        int countOfSub = messageLen / MAX_LINE_LENGTH;
        int index = 0;
        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                Log.println(level, tag, message.substring(index, index + MAX_LINE_LENGTH));
                index += MAX_LINE_LENGTH;
            }
        }
        if (index != messageLen) {
            Log.println(level, tag, message.substring(index, messageLen));
        }
    }
}
