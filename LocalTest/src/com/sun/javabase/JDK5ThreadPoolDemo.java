package com.sun.javabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * jre5线程池demo  线程池使用demo
 * @author sunx(sunxin@strongit.com.cn)<br/>
 * @version V1.0.0<br/>
 * @see {@link }
 */

public class JDK5ThreadPoolDemo {
    public static void main(String[] args) {
        fixedThreadPool();
    }

    /**
     * 创建一个缓存线程池(无限大)，如果线程池超过了处理需要，可灵活的回收多余线程。<br>
     * 适合：已知任务量适中且与时间为线性关系（因为线程池无限大，不进行并发数量的控制）<br>
     */
    public static void catchedThreadPool() {
        // 缓存线程池
        ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

        for (int i = 0; i < 10; i++) {
            final int index = i;
            cachedThreadPool.execute(new Runnable() {
                public void run() {
                    // TODO 任务内容
                    System.out.println(index);
                    try {
                        Thread.sleep(index * 1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 定长线程池，可控制并发线程数，超出线程进入等待状态<br>
     * 适合：任务数量已固定，等待线程处理结束<br>
     */
    public static void fixedThreadPool() {
        ExecutorService fixedThreadPool = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 10; i++) {
            final int index = i;
            fixedThreadPool.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    /**
     * 周期线程池，任务可周期性执行（如 定时执行，周期执行）<br>
     * !0定时执行<br>
     * 0周期执行 <br>
     * 适合：定时任务<br>
     */
    public static void scheduledThreadPool(int choose) {

        if (choose != 0) {
            // 定时执行，3秒后执行
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.schedule(new Runnable() {
                public void run() {
                    System.out.println("delay 3 seconds");
                }
            }, 3, TimeUnit.SECONDS);
        } else {
            // 周期执行， 1秒后开始，每3秒执行一次
            ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
            scheduledThreadPool.scheduleAtFixedRate(new Runnable() {
                public void run() {
                    System.out.println("delay 1 seconds, and excute every 3 seconds");
                }
            }, 1, 3, TimeUnit.SECONDS);
        }
    }

    /**
     * 单线程线程池（线程池内只存在一个工作线程，用于保证执行顺序）<br>
     * 适合： 个人认为 仅为提供更优的执行，如 若线程异常会创建新线程替代，不会出现因异常中断的情况<br>
     *
     */
    public static void singleThreadExector() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            final int index = i;
            singleThreadExecutor.execute(new Runnable() {
                public void run() {
                    try {
                        System.out.println(index);
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
