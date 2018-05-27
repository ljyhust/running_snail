package com.ljyhust.algorithm;

/**
 * 动态规划解决斐波那契数列
 * Created by Administrator on 2018/5/27.
 */
public class FibonacciSolution {

    /**
     * 相当与结合循环用归纳法解决问题
     * @param n 从1开始，如果n从0开始，则nextToLast=0初始化值
     * @return
     */
    public int fibonacci(int n) {
        if(n <= 1) {
            return 1;
        }
        int last = 1;  // f(n-1)
        int nextToLast = 1;  //f(n-2)
        int res = 1;
        for(int i = 2; i <= n; i++) {
            res = last + nextToLast;
            nextToLast = last;
            last = res;
        }
        return res;
    }
}
