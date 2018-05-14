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

}
