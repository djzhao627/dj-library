package cn.djzhao.library.log;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * 日志对象实体类
 *
 * @author djzhao
 * @date 21/01/02
 */
public class DJLogModel {
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd HH:mm:ss", Locale.CHINA);
    private long timeMillis;
    private int level;
    private String tag;
    private String message;

    public DJLogModel(long timeMillis, int level, String tag, String message) {
        this.timeMillis = timeMillis;
        this.level = level;
        this.tag = tag;
        this.message = message;
    }

    public String getFlattenedLog() {
        return getFlattened() + '\n' + message;
    }

    public String getFlattened() {
        return format(timeMillis) + '|' + level + '|' + tag + "|:";
    }

    private String format(long timeMillis) {
        return sdf.format(timeMillis);
    }

    public long getTimeMillis() {
        return timeMillis;
    }

    public void setTimeMillis(long timeMillis) {
        this.timeMillis = timeMillis;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
