package cn.djzhao.library.log.printer;

import androidx.annotation.NonNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

import cn.djzhao.library.log.DJLogConfig;
import cn.djzhao.library.log.DJLogModel;

/**
 * 日志文件打印器
 *
 * @author djzhao
 * @date 21/01/03
 */
public class DJFilePrinter implements DJLogPrinter {

    private static final ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;
    private LogWriter writer;
    private PrintWorker worker;
    private static DJFilePrinter instance;

    /**
     * 创建DJFilePrinter
     *
     * @param logPath       log保存的路径，如是外部路径需要确保已有读写权限
     * @param retentionTime log文件的有效时长，单位毫秒；<= 0表示一直有效
     * @return instance
     */
    public static synchronized DJFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new DJFilePrinter(logPath, retentionTime);
        }
        return instance;
    }

    private DJFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.writer = new LogWriter();
        this.worker = new PrintWorker();
        cleanExpiredLog();
    }

    /**
     * 清除过期的log文件
     */
    private void cleanExpiredLog() {
        if (retentionTime <= 0) {
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null) {
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime) {
                file.delete();
            }
        }
    }

    @Override
    public void print(@NonNull DJLogConfig config, int level, String tag, @NonNull String message) {
        long currentTimeMillis = System.currentTimeMillis();
        if (!worker.isRunning()) {
            worker.start();
        }
        worker.put(new DJLogModel(currentTimeMillis, level, tag, message));
    }

    /**
     * 新线程工作对象，BlockingQueue的使用，防止频繁的创建线程
     */
    private class PrintWorker implements Runnable {
        // LinkedBlockingQueue没有数据时，使用take会阻塞
        private BlockingQueue<DJLogModel> logs = new LinkedBlockingQueue<>();
        private volatile boolean running;

        /**
         * 将log放入打印队列
         *
         * @param model 需要打印的log
         */
        void put(DJLogModel model) {
            try {
                logs.put(model);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断线程是否还在运行
         *
         * @return 是否运行
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        /**
         * 启动工作线程
         */
        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            DJLogModel log;
            try {
                while (true) {
                    log = logs.take();
                    doPrint(log);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
                synchronized (this) {
                    running = false;
                }
            }
        }
    }

    /**
     * 调用LogWriter进行log打印
     *
     * @param log 日志
     */
    private void doPrint(DJLogModel log) {
        String lastFileName = writer.getPreFileName();
        if (lastFileName == null) {
            String newFileName = generateFileName();
            if (writer.isReady()) {
                writer.close();
            }
            if (!writer.ready(newFileName)) {
                return;
            }
        }
        writer.append(log.getFlattenedLog());
    }

    /**
     * 生成文件名："yyyy-MM-dd"
     *
     * @return 文件名
     */
    private String generateFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(System.currentTimeMillis()) + ".log";
    }

    /**
     * 基于BufferedWriter进行log文件写入
     */
    private class LogWriter {
        private String preFileName;
        private File logFile;
        private BufferedWriter bufferedWriter;

        boolean isReady() {
            return bufferedWriter != null;
        }

        String getPreFileName() {
            return preFileName;
        }

        /**
         * log写入前的准备
         *
         * @param newFileName 要保存的文件名
         * @return 是否就绪
         */
        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, newFileName);

            if (!logFile.exists()) {
                try {
                    File parent = logFile.getParentFile();
                    if (!parent.exists()) {
                        parent.mkdirs();
                    }
                    logFile.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                    preFileName = null;
                    logFile = null;
                    return false;
                }
            }

            try {
                bufferedWriter = new BufferedWriter(new FileWriter(logFile, true));
            } catch (IOException e) {
                e.printStackTrace();
                preFileName = null;
                logFile = null;
                return false;
            }
            return true;
        }

        /**
         * 关闭bufferedWriter
         *
         * @return 是否关闭
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return false;
                } finally {
                    bufferedWriter = null;
                    preFileName = null;
                    logFile = null;
                }
            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param message log信息
         */
        void append(String message) {
            try {
                bufferedWriter.write(message);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
