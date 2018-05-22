package com.ljyhust.algorithm;

/**
 * 用堆实现TopK
 * 找最大TopK用小堆
 * 找最小TopK用大堆
 * 思路(以最大TopK为例)：
 *    1.建立K长度的堆，并输入前K个数据buildHeap
 *    2.从K+1个输入开始，判断堆顶，若input>堆顶，删除堆顶，并调整堆
 *    3.若input<堆顶，忽略不处理
 */
public class TopKthWithHeap {

    private int k;

    private int[] topk;

    public int[] dealWithInput(int[] array) {
        // 判断K个元素
        int len = array.length;

        if (len > k) {
            topk = new int[k];
        } else {
            topk = new int[len];
        }

        for (int n = 0; n < topk.length; n++) {
            topk[n] = array[n];
        }
        // 构建堆
        buildHeap();
        if (len > k) {
            for (int i = k; i < len; i++) {
                if (array[i] > topk[0]) {
                    insertNodeAndDeleteMin(array[i]);
                }
            }
        }
        return topk;
    }

    /**
     * 堆元素从0开始，父节点i/2 - 1，子节点2i+1，2i+2
     * @param x
     * @return
     */
    private void insertNodeAndDeleteMin(int x) {
        topk[0] = x;
        precDown(0);
    }

    /**
     * 下滤操作
     * @param hole 要下滤的元素索引
     */
    private void precDown(int hole) {
        int temp = topk[hole];
        int child;  //子节点, 数值较小的那个
        for(; hole <= (topk.length / 2 - 1); hole = child) {
            child = 2 * hole + 1;
            if (child != topk.length
                    && topk[child] > topk[child + 1]) {
                child++;
            }
            if (temp > topk[child]) {
                topk[hole] = topk[child];
            } else {
                break;
            }
        }
        topk[hole] = temp;
    }

    /**
     * 构建heap
     */
    private void buildHeap() {
        for (int i = topk.length / 2 - 1; i >= 0; i--) {
            precDown(i);
        }
    }
}
