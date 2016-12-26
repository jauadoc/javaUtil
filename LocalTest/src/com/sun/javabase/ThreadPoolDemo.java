package com.sun.javabase;

import java.util.LinkedList;

/**
 * 线程池demo  <br>
 * 线程池的基本实现原理<br>
 * @author sunx(sunxin@strongit.com.cn)
 * @version V0.0.1
 */
public class ThreadPoolDemo{

    /**
     * 线程池中线程数
     */
    private int threadNum = 10;
    /**
     * 任务工作者集合（线程池，存放若干线程，不间断的从任务队列中取任务并执行）
     */
    private final PoolWorker[] pool;
    /**
     * 任务队列
     */
    private final LinkedList<Runnable> queue;

    /**
     * 初始化线程池
     * 1.创建线程池
     * 2.设定线程池中线程数
     * 3.按照需要的线程数创建线程池工作者，并加入到线程池中
     * 4.线程池工作者开始工作
     *
     * @param threadNum
     */
    public ThreadPoolDemo(int threadNum) {

        this.threadNum = threadNum;
        queue = new LinkedList<Runnable>();
        pool = new PoolWorker[threadNum];

        for (int i = 0; i < threadNum; i++) {
            pool[i] = new PoolWorker();
            pool[i].start();
        }
    }

    /**
     * 执行任务
     * 1.在任务队列中加入任务
     * 2.唤醒所有挂起的任务工作者线程
     * @param r
     */
    public void execute(PoolWorker r) {
        synchronized (queue) {
            queue.addLast(r);
            queue.notify();
        }
    }

    /**
     * 线程池工作者
     * 1.实现线程方法
     * 2.while(true)
     * 3.从任务队列取任务，并执行任务。
     *      1.有   执行任务
     *      2.无   挂起该工作者，等待任务队列中加入任务后唤醒该工作者线程。
     * @author sunx(sunxin@strongit.com.cn)
     * @version V0.0.1
     */
    private class PoolWorker extends Thread {
        public void run() {
            Runnable r;
            while (true) {
                synchronized (queue) {
                    while (queue.isEmpty()) {
                        try {
                            queue.wait();
                        } catch (InterruptedException ignored) {
                        }
                    }
                    r = (Runnable) queue.removeFirst();
                }
                try {
                    r.run();
                } catch (RuntimeException e) {
                }
            }
        }
    }
}
