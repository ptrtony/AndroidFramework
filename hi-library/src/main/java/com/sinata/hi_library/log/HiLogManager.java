package com.sinata.hi_library.log;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author cjq
 * @Date 4/4/2021
 * @Time 7:11 PM
 * @Describe:
 */
public class HiLogManager {

    private static HiLogManager instance;
    private HiLogConfig logConfig;
    private List<HiLogPrinter> hiLogPrinters = new ArrayList<>();

    private HiLogManager(HiLogConfig hiLogConfig, HiLogPrinter[] printers) {
        this.logConfig = hiLogConfig;
        this.hiLogPrinters.addAll(Arrays.asList(printers));
    }

    public static HiLogManager getInstance() {
        return instance;
    }

    public static void init(@NotNull HiLogConfig hiLogConfig, HiLogPrinter... printers) {
        instance = new HiLogManager(hiLogConfig, printers);
    }

    public HiLogConfig getLogConfig() {
        return logConfig;
    }

    public List<HiLogPrinter> getPrinters() {
        return hiLogPrinters;
    }

    public void addPrinter(HiLogPrinter printer) {
        hiLogPrinters.add(printer);
    }

    public void removePrinter(HiLogPrinter printer) {
        if (hiLogPrinters != null) {
            hiLogPrinters.remove(printer);
        }
    }
}
