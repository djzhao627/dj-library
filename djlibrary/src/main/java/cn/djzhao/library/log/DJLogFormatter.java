package cn.djzhao.library.log;

/**
 * 日志格式化接口
 *
 * @author djzhao
 * @date 20/12/31
 */
public interface DJLogFormatter<T> {
    String format(T data);
}
