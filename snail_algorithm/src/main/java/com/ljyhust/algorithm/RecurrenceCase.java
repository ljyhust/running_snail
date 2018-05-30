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

    /**
     * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
     * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
     * @param target
     * @return
     */
    public int rectCover(int target) {
        /**
         * 1.先确定长度为2的边，底部只有两种方式横或竖
         * 2.如果第一个横着放，则长度2已确定，其余可以在上面任意摆放2*(n-1)；
         * 3.如果第一个竖着放，则还需要一个并排共同确定底部长度为2，其余n-2在上面任意摆放成2*(n-2)
         * 综上，本题其实就是斐波那契数列f(1)=1, f(2)=2
         */
        if (target <= 2) {
            return target;
        }
        int res = 1;
        int last = 2;
        int nextToLast = 1;
        for(int i = 3; i <= target; i++) {
            res = last + nextToLast;
            nextToLast = last;
            last = res;
        }
        return res;
    }
}
