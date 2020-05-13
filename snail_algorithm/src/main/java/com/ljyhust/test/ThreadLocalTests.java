package com.ljyhust.test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by lijinyang on 2020/4/15.
 * 验证
 */
public class ThreadLocalTests {

    private static ThreadLocal<String> threadLocal1 = new ThreadLocal<>();
    private static ThreadLocal<String> threadLocal2 = new ThreadLocal<>();

    public static void main(String[] args) {

        threadLocal1.set("主线程数据1");
        threadLocal2.set("主线程数据2");

        new Thread(new Runnable() {
            @Override
            public void run() {
                threadLocal1.set("副线程数据1");
                threadLocal2.set("副线程数据2");
            }
        }).start();

        threadLocal1.get();
        threadLocal2.get();
    }
}
