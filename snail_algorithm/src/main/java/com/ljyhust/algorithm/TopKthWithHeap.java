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

    public void dealWithInput(int[] array) {
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

        HeapStruct heap = new HeapStruct(topk);
        if (len > k) {
            for (int i = k; i < len; i++) {
                if (array[i] > topk[1]) {
                    heap.deleteMin();
                }
                heap.insert(array[i]);
            }
        }
    }
}
