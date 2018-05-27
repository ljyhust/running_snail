package com.ljyhust.algorithm;

/**
 * 用递归算法解决的问题
 * Created by Administrator on 2018/5/27.
 */
public class RecurrenceCase {

    /**
     * 跳台阶问题：一只青蛙一次可以跳上1级台阶，也可以跳上2级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
     * @param target 目标级数,target>0
     * @return
     */
    public int jumpFloor(int target) {
        // 归纳分析，n级台阶是n-1级台阶（种）+ 1 与 n-2级台阶（种数）+ 2 的和
        // 因此这个是斐波那契数列
        if (target <= 2) {
            return target;
        }
        int res = 1;
        int last = 2;  //f(n-1)
        int nextToLast = 1;  //f(n-2)
        for(int i = 3; i <= target; i++) {
            res = last + nextToLast;
            nextToLast = last;
            last = res;
        }
        return res;
    }
}
