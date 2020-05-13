package com.ljyhust.algorithm.sort;

import com.ljyhust.algorithm.ListNode;

/**
 * 排序算法
 * Created by ljyhust on 2020/3/19.
 */
public class SortAlgorithm {

    // 插入排序，适用于数据，时间复杂度O(N^2)
    static class InsertSort{

        public void sort(int[] array) {
            for(int i = 1; i < array.length; i++) {
                int temp = array[i];
                int j = i;
                for(; j > 0 && temp < array[j - 1]; j--) {
                    array[j] = array[j - 1];
                }
                array[j] = temp;
            }
        }
    }

    /**
     * 堆排序
     * 1. 构建堆，通过下滤方法构建max堆
     * 2. 交换第1个与最末尾数据
     * 3. 下滤
     */
    static class HeapSort {

        public int leftChild(int i) {
            return 2 * i + 1;
        }

        /**
         * 下滤方法
         * @param array
         * @param point 需要交换的元素（空穴 hole）
         * @param currentSize 数据大小
         */
        public void preDown(int[] array, int point, int currentSize) {
            int child;
            int temp;
            for(temp = array[point]; leftChild(point) < currentSize; point = child) {
                child = leftChild(point);
                if (child != currentSize - 1 && array[child] < array[child + 1]) {
                    child++;
                }
                // 父节点小于子节点，父节点下移
                if (temp < array[child]) {
                    array[point] = array[child];
                } else {
                    break;
                }
            }
            array[point] = temp;
        }

        public void swapArray(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }

        public void sort(int[] array) {
            // 构建max堆
            for(int i = array.length / 2 - 1; i >= 0; i--) {
                preDown(array, i, array.length);
            }
            // 排序
            for(int i = array.length - 1; i >= 0; i--) {
                swapArray(array, 0, i);
                preDown(array, 0, i);
            }
        }
    }

    /**
     * 归并排序 时间O(N logN) 空间O(N)
     */
    static class MergeSort {
        //递归分治
        //排序
        //复制已排好的元素

        /**
         * 递归分治
         * @param array
         * @param tempArray
         * @param left
         * @param right
         */
        void mergeSort(int[] array, int[] tempArray, int left, int right) {
            if (left >= right) {
                return;
            }
            //中间节点
            int centerIndex = (left + right) / 2;
            mergeSort(array, tempArray, left, centerIndex);
            mergeSort(array, tempArray, centerIndex + 1, right);
            //排序
            merge(array, tempArray, left, centerIndex, right);
        }

        void merge(int[] array, int[] tempArray, int left, int center, int right) {
            int leftEnd = center;
            int rightPos = center + 1;
            // 排序元素总大小
            int size = right - left + 1;
            int tempIndex = left;

            while(left <= leftEnd && rightPos <= right) {
                if (array[left] <= array[rightPos]) {
                    tempArray[tempIndex++] = array[left++];
                } else {
                    tempArray[tempIndex++] = array[rightPos++];
                }
            }

            // copy剩下的部分数组
            while(left <= leftEnd) {
                tempArray[tempIndex++] = array[left++];
            }

            while (rightPos <= right) {
                tempArray[tempIndex++] = array[rightPos++];
            }

            // copy排序好的数组到array
            for (int i = 0; i < size; i++, right--) {
                array[right] = tempArray[right];
            }

        }
    }

    /**
     * 快速排序，最后的小数组需要用插入排序来解决
     * 1. 找到中值方法
     * 2. 排序： 左 右 分别与中值比较，交换中值位置
     * 3. 分治：左侧小 右侧大 再排序
     */
    static class QuickSort {

        /**
         * 快速排序主方法
         * @param array
         * @param left
         * @param right
         */
        public void quickSort(int[] array, int left, int right) {
            if (left >= right) {
                return;
            }
            int middleValue = middleValue(array, left, right);
            // FIXME 如果right - left + 1 <= 3 直接返回，不用排序，因为middleValue方法已经完成排序
            if (right - left <= 2) {
                return;
            }
            int i = left;
            int j = right - 1;
            for(;;) {
                while (array[++i] < middleValue){}
                while (array[--j] > middleValue){}
                // 遇到小值在j 或 大值在i 位置时，交换
                if (i < j) {
                    swapIndex(array, i, j);
                } else {
                    break;
                }
            }
            // 结束比较，交换middle 在正确位置
            swapIndex(array, i, right - 1);
            quickSort(array, left, i - 1);
            quickSort(array, i + 1, right);
        }

        /**
         * 获取枢纽元，并将该值放入right - 1位置
         * 先计算到中值存入center位置，再交换center  right - 1位置
         * @param array
         * @param left
         * @param right
         * @return
         */
        public int middleValue(int[] array, int left, int right) {
            int center = (left + right) / 2;
            // 前两步判断保证 left 最小
            if (array[center] < array[left]) {
                swapIndex(array, center, left);
            }
            if (array[right] < array[left]) {
                swapIndex(array, left, right);
            }
            // 最后比较中间值 和 右侧值
            if (array[center] > array[right]) {
                swapIndex(array, center, right);
            }

            swapIndex(array, center, right - 1);
            return array[right - 1];
        }

        /**
         * 交换位置
         * @param array
         * @param i
         * @param j
         */
        public void swapIndex(int[] array, int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    /**
     * 链表排序 时间 O(N logN) 空间 O(1)
     * 分析 O(N logN) -> 排序目前有 堆排序（适合带索引号的数组）  归并排序
     */
    static class LinkedListSort {

        /**
         * 获取链表的中间节点，单双指针
         * @param linkedList
         * @return
         */
        public ListNode getMiddleNode(ListNode linkedList) {
            if (null == linkedList || null == linkedList.next) {
                return linkedList;
            }
            ListNode point = linkedList;
            ListNode evenNode = point.next.next;
            while (null != evenNode && null != evenNode.next && null != evenNode.next.next) {
                point = point.next;
                evenNode = evenNode.next.next;
            }
            return point;
        }

        public ListNode sortLinkedList(ListNode listNode) {
            if (null == listNode || null == listNode.next) {
                return listNode;
            }
            ListNode middleNode = getMiddleNode(listNode);
            ListNode rightNodePos = middleNode.next;
            middleNode.next = null;
            ListNode leftNode = sortLinkedList(listNode);
            ListNode rightNode = sortLinkedList(rightNodePos);
            return merge(leftNode, rightNode);
        }

        public ListNode merge(ListNode leftNode, ListNode rightNode) {
            ListNode leftPos = leftNode;
            ListNode rightPos = rightNode;
            ListNode tempNode = null;
            ListNode point = null;
            while (leftPos != null && rightPos != null) {
                if (leftPos.val < rightPos.val) {
                    if (null == tempNode) {
                        tempNode = leftPos;
                        point = tempNode;
                    } else {
                        point.next = leftPos;
                        point = point.next;
                    }
                    leftPos = leftPos.next;
                } else {
                    if (null == tempNode) {
                        tempNode = rightPos;
                        point = tempNode;
                    } else {
                        point.next = rightPos;
                        point = point.next;
                    }
                    rightPos = rightPos.next;
                }
            }

            if (null != leftPos) {
                point.next = leftPos;
            }

            if (null != rightPos) {
                point.next = rightPos;
            }

            return tempNode;
        }
    }


    public static void main(String[] args) {
        int[] array = {34, 65, 12, 9, 76, 21};

        /*InsertSort insertSort = new InsertSort();
        insertSort.sort(array);
        for (int i : array) {
            System.out.println(i);
        }*/

        /*HeapSort heapSort = new HeapSort();
        heapSort.sort(array);
        System.out.println("====> 堆排序");
        for (int i : array) {
            System.out.println(i);
        }*/

        /*MergeSort mergeSort = new MergeSort();
        int[] tempArray = new int[array.length];
        mergeSort.mergeSort(array, tempArray, 0, array.length - 1);*/

        QuickSort quickSort = new QuickSort();
        quickSort.quickSort(array, 0, array.length - 1);
        for (int i : array) {
            System.out.println(i);
        }
    }
}
