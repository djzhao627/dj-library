package cn.djzhao.library.log.formatter;

/**
 * 线程格式化器
 *
 * @author djzhao
 * @date 20/12/31
 */
public class DJThreadFormatter implements DJLogFormatter<Thread> {
    @Override
    public String format(Thread data) {
        return "Thread:" + data.getName();
    }
}
