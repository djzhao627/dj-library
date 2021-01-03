package cn.djzhao.library.log;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import cn.djzhao.library.log.printer.DJLogPrinter;

/**
 * DJLog管理类
 */
public class DJLogManger {
    private DJLogConfig config;
    private static DJLogManger instance;

    private List<DJLogPrinter> printers = new ArrayList<>();

    private DJLogManger(DJLogConfig config, DJLogPrinter[] printers) {
        this.config = config;
        this.printers.addAll(Arrays.asList(printers));
    }

    public static DJLogManger getInstance() {
        return instance;
    }

    public static void init(@NonNull DJLogConfig config, DJLogPrinter... printers) {
        if (instance == null) {
            instance = new DJLogManger(config, printers);
        }
    }

    public DJLogConfig getConfig() {
        return config;
    }

    public List<DJLogPrinter> getPrinters() {
        return printers;
    }

    public void addPrinter(DJLogPrinter printer) {
        printers.add(printer);
    }

    public void removePrinter(DJLogPrinter printer) {
        if (printer != null) {
            printers.remove(printer);
        }
    }
}
