package com.sinata.hi_library.log;


import android.annotation.SuppressLint;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * @author cjq
 * @Date 9/4/2021
 * @Time 10:40 PM
 * @Describe:
 */
public class HiFilePrinter implements HiLogPrinter {
    private static ExecutorService EXECUTOR = Executors.newSingleThreadExecutor();
    private final String logPath;
    private final long retentionTime;
    private LogWriter logWriter;
    private volatile PrintWorker printWorker;
    private static HiFilePrinter instance;

    private HiFilePrinter(String logPath, long retentionTime) {
        this.logPath = logPath;
        this.retentionTime = retentionTime;
        this.logWriter = new LogWriter();
        this.printWorker = new PrintWorker();
        cleanExpiredLog();
    }

    /**
     * 创建HiFilePrinter
     *
     * @param logPath       log保存路径，如果是外部路径需要确保已经有外部存储的读写权限
     * @param retentionTime log文件的有效时长，单位毫秒，<=0表示一直有效
     */
    public static synchronized HiFilePrinter getInstance(String logPath, long retentionTime) {
        if (instance == null) {
            instance = new HiFilePrinter(logPath, retentionTime);
        }
        return instance;
    }


    @Override
    public void print(@NotNull HiLogConfig config, int level, String tag, @NotNull String printString) {

        long timeMillis = System.currentTimeMillis();
        if (!printWorker.isRunning()) {
            printWorker.start();
        }
        printWorker.put(new HiLogMo(timeMillis, level, tag, printString));
    }


    private class PrintWorker implements Runnable {

        private BlockingQueue<HiLogMo> logs = new LinkedBlockingDeque<>();
        private volatile boolean running;

        /**
         * 将log放入打印队列
         *
         * @param log 要被打印的log
         */
        void put(HiLogMo log) {
            try {
                logs.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        /**
         * 判断工作线程是否还在运行中
         *
         * @return true 在运行
         */
        boolean isRunning() {
            synchronized (this) {
                return running;
            }
        }

        void start() {
            synchronized (this) {
                EXECUTOR.execute(this);
                running = true;
            }
        }

        @Override
        public void run() {
            HiLogMo log;
            synchronized (this) {
                try {
                    log = logs.take();
                    doPrint(log);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    running = false;
                }
            }
        }
    }

    private void doPrint(HiLogMo log) {
        String lastFileName = logWriter.getPreFileName();
        if (lastFileName == null) {
            String newFileName = genNewFileName();
            if (logWriter.isReady()) {
                logWriter.close();
            }

            if (!logWriter.ready(newFileName)) {
                return;
            }
        }
        logWriter.append(log.getFlattened());
    }

    @SuppressLint("SimpleDateFormat")
    private String genNewFileName() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        sdf.setTimeZone(TimeZone.getDefault());
        return sdf.format(new Date(System.currentTimeMillis()));
    }

    /**
     * 清除过期log
     */
    private void cleanExpiredLog(){
        if (retentionTime <= 0){
            return;
        }
        long currentTimeMillis = System.currentTimeMillis();
        File logDir = new File(logPath);
        File[] files = logDir.listFiles();
        if (files == null){
            return;
        }
        for (File file : files) {
            if (currentTimeMillis - file.lastModified() > retentionTime){
                file.delete();
            }
        }
    }


    /**
     * 基于BufferedWriter将log写入文件
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
         * log写入前的准备操作
         *
         * @param newFileName 要保存log的文件名
         * @return true 表示准备就绪
         */
        boolean ready(String newFileName) {
            preFileName = newFileName;
            logFile = new File(logPath, preFileName);

            if (!logFile.exists()) {
                File parentFile = logFile.getParentFile();
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                try {
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
         */
        boolean close() {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    bufferedWriter = null;
                    logFile = null;
                    preFileName = null;
                    return false;
                }

            }
            return true;
        }

        /**
         * 将log写入文件
         *
         * @param flattenedLog 格式化后的log
         */
        void append(String flattenedLog) {
            if (bufferedWriter != null) {
                try {
                    bufferedWriter.write(flattenedLog);
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


}
