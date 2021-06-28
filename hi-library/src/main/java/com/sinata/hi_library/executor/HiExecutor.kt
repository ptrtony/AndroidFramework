package com.sinata.hi_library.executor

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.annotation.IntRange
import java.util.concurrent.*
import java.util.concurrent.atomic.AtomicLong
import java.util.concurrent.locks.Condition
import java.util.concurrent.locks.ReentrantLock

/**

Title:
Description:
Copyright:Copyright(c)2021
Company:成都博智维讯信息技术股份有限公司


@author jingqiang.cheng
@date 26/6/2021
 */

/**
 * 支持按任务的优先级去执行
 * 支持线程池暂停，恢复(批量文件下载,上传)
 * 异步结果主动回调主线程
 * todo 线程池能力监控，耗时任务检测，定时，延迟
 */
object HiExecutor {
    private const val TAG = "HiExecutor"
    private var newCondition: Condition
    private var isPause: Boolean = false
    private var hiExecutor: ThreadPoolExecutor
    private var lock: ReentrantLock
    private var mainHandler: Handler

    init {
        val cupCount = Runtime.getRuntime().availableProcessors()
        val corePoolSize = cupCount + 1
        val maxPoolSize = cupCount * 2 + 1
        val blockingQueue: PriorityBlockingQueue<out Runnable> = PriorityBlockingQueue()
        val keepAliveTime = 30L
        val unit = TimeUnit.SECONDS
        val seq = AtomicLong()
        mainHandler = Handler(Looper.getMainLooper())
        val threadFactory = ThreadFactory {
            val thread = Thread(it)
            //hi-thread -0
            thread.name = "hi-executor-" + seq.getAndIncrement()
            return@ThreadFactory thread
        }
        lock = ReentrantLock()
        newCondition = lock.newCondition()
        hiExecutor = object : ThreadPoolExecutor(
            corePoolSize,
            maxPoolSize,
            keepAliveTime,
            unit,
            blockingQueue as BlockingQueue<Runnable>
        ) {
            override fun beforeExecute(t: Thread?, r: Runnable?) {
                if (isPause) {
                    try {
                        lock.lock()
                        newCondition.await()
                    } finally {
                        lock.unlock()
                    }
                }
            }

            override fun afterExecute(r: Runnable?, t: Throwable?) {
                //监控线程池耗时任务，线程创建数量，正在运行的数量
                Log.d(TAG,"已执行完的任务的优先级是：" + (r as PriorityRunnable).priority)
            }
        }
    }

    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Runnable) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }


    @JvmOverloads
    fun executor(@IntRange(from = 0, to = 10) priority: Int = 0, runnable: Callable<*>) {
        hiExecutor.execute(PriorityRunnable(priority, runnable))
    }


    abstract class Callable<T> : Runnable {
        override fun run() {
            mainHandler.post {
                onPrepare()
            }
            val t = onBackground()
            mainHandler.post {
                onCompleted(t)
            }
        }

        open fun onPrepare() {

        }

        abstract fun onBackground(): T

        abstract fun onCompleted(t: T)


    }


    class PriorityRunnable(val priority: Int, val runnable: Runnable) : Runnable,
        Comparable<PriorityRunnable> {

        override fun compareTo(other: PriorityRunnable): Int {
            return if (this.priority < other.priority) 1 else if (this.priority > other.priority) -1 else 0
        }

        override fun run() {
            runnable.run()
        }

    }

    @Synchronized
    fun pause() {
        isPause = true
        Log.d(TAG, "executor is paused")
    }

    @Synchronized
    fun resume() {
        isPause = false
        lock.lock()
        try {
            newCondition.signalAll()
        } finally {
            lock.unlock()
        }
        Log.d(TAG, "executor is resume")
    }
}