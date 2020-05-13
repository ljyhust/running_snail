package com.ljyhust.test;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by lijinyang on 2020/4/16.
 */
@Slf4j
public class AQSTests {

    private static volatile boolean flag;
    private static int len;

    public static void main(String[] args) throws InterruptedException {
        log.info("====> 测试线程中断");
        //LockSupport.park();
        Thread.currentThread().interrupt();
        log.info("====> 线程未中断，继续执行，只是个标志位");
        log.info("====> 线程未中断，继续执行，只是个标志位");
        log.info("====> 线程未中断，继续执行，只是个标志位");
        log.info("====> 线程未中断，继续执行，只是个标志位");
        //Thread.sleep(1000);
        log.info("====> 线程未中断，继续执行，只是个标志位");
        log.info("====> 线程未中断，继续执行，只是个标志位");
        log.info("====> 线程未中断，继续执行，只是个标志位");

        ReentrantLock lock = new ReentrantLock();
        // 测试Lock
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("=====> enter thread 1");
                lock.lock();
                log.info("=====> thread 1 get lock");
                try {
                    Thread.sleep(30000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.unlock();
                log.info("=====> thread 1 release lock");
            }
        });
        thread1.setName("thread1");


        // 测试Lock
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                log.info("=====> enter thread 2");
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                lock.lock();
                try {
                    Thread.sleep(300);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                log.info("=====> thread 2 get lock");
                lock.unlock();
                log.info("=====> thread 2 release lock");
            }
        });
        thread2.setName("thread2");

        thread1.start();
        thread2.start();

        test();
    }

    private static int test() {

        for(; ;) {
            if (flag) {
                log.info("===");
            }
        }
    }
}
