package com.ljyhust.algorithm;

/**
 * 堆（优先队列）操作
 * 结构：如果根节点索引是0，父节点(i-1)/2；子节点2i+1  2i+2
 *      如果根节点索引是1, 父节点i/2; 子节点2i  2i+1
 * 完全二叉堆，底层由数组实现，主要操作有insert、deleteMin、buildHeap、MergeHeap
 * Created by ljyhust on 2018/5/13.
 */
public class HeapStruct {

    private static int capacity = 11;
    private int[] arr = new int[capacity];

    private int size = 0;

    public HeapStruct(int[] array) {
        buildArrayHeap(array);
    }

    /**
     * 添加x元素
     * 1.++size添加一个空位
     * 2.上滤将x插入正确的位置
     * @param x
     */
    public void insert(int x) {
        // hole是空穴位置
        int hole = ++size;
        for(; arr[hole / 2] > x; hole = hole / 2) {
            // 如果父节点hole/2值>x, 那么将父节点下拉到子节点中
            arr[hole] = arr[hole / 2];
        }
        arr[hole] = x;
    }

    /**
     * 删除最小的元素
     * 1.最小的元素一定是a[1]
     * 2.--size，把最后一个元素游离出来，通过下滤将元素放入正确位置
     * @return
     */
    public int deleteMin() {
        int temp = arr[--size];
        // 把temp放入具体位置
        // 删除第1个，则第1个位置空了，成了hole
        int hole = 1;
        int child;
        /*for(; 2 * hole <= size; hole = child) {
            child = 2 * hole;  //子节点
            if (child != size
                    && arr[child] > arr[child + 1]) {
                child++;
            }
            // 如果子节点太小，则把子节点放到该节点上（hole）
            if (arr[child] < temp) {
                arr[hole] = arr[child];
            } else {
                break;
            }
        }
        arr[hole] = temp;*/
        precDown(hole);
        return arr[1];
    }

    /**
     * 下滤方法
     * hole空穴位置
     */
    private void precDown(int hole) {
        int child;
        int temp = arr[hole];
        for(; 2 * hole <= size; hole = child) {
            child = 2 * hole;
            // 子节点最小值
            if (child != size
                    && arr[child] > arr[child + 1]) {
                child++;
            }
            // 子节点比temp值小，则将子节点放入hole上
            if (arr[child] < temp) {
                arr[hole] = arr[child];
            } else {
                break;
            }
        }
        arr[hole] = temp;
    }

    /**
     * 建立堆
     * @param inputArr
     */
    private void buildArrayHeap(int[] inputArr) {
        this.arr = new int[inputArr.length + 1];
        size = inputArr.length;
        for(int i = 0; i < inputArr.length; i++) {
            arr[i + 1] = inputArr[i];
        }
        // 从非叶节点开始下滤操作
        for (int hole = size / 2; hole > 0; hole--) {
            precDown(hole);
        }
    }

    /**
     * 合并堆操作, 二叉堆合并不是其强项，如果一定要，那么通过复制到同一数组
     * 然后通过建堆操作合并
     * @param other
     * @return
     */
    public HeapStruct merge(HeapStruct other) {
        return null;
    }

}
