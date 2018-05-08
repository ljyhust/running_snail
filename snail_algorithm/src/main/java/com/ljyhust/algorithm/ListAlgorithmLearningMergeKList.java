package com.ljyhust.algorithm;

/**
 * 算法练习--链表，合并k个有序链表
 * Created by Administrator on 2018/5/8.
 */
public class ListAlgorithmLearningMergeKList {

    /**
     * 合并 k 个排序链表，返回合并后的排序链表。请分析和描述算法的复杂度。
     *
     * 采用归并排序法，每个链表都有一个当前的指针表示当前归并进行到哪个节点
     * 小Tip，对于链表可不用额外的指针，用表头表示即可
     * 用递归
     * @param lists
     * @return
     */
    public ListNode mergeKLists(ListNode[] lists) {
        // 清理输入数据
        int length = 0;
        for (ListNode elem : lists) {
            if (null != elem) {
                length++;
            }
        }
        if (0 == length) {
            return null;
        } else {
            ListNode[] listNodes = new ListNode[length];
            int i = 0;
            for (ListNode elem : lists) {
                if (null != elem) {
                    listNodes[i] = elem;
                    i++;
                }
            }
            return mergeKListsNotNull(listNodes);
        }
    }

    public ListNode mergeKListsNotNull(ListNode[] lists) {
        // 递归基准
        if(lists.length == 1) {
            return lists[0];
        }
        // 找最小的，然后去掉表头
        int min = minNode(lists);
        ListNode tempNode = lists[min];
        lists[min] = lists[min].next;
        // 去除为null的ListNode
        ListNode[] listNodes = filterNullLists(lists, min);
        tempNode.next = mergeKLists(listNodes);

        return tempNode;
    }

    /**
     * 比较每个表头val的大小，返回最小的链表数组index
     * @param lists
     * @return
     */
    public int minNode(ListNode[] lists) {
        int index = 0;
        ListNode minNode = lists[index];
        for (int i = 1; i < lists.length; i++) {
            if (minNode.val > lists[i].val) {
                index = i;
                minNode = lists[i];
            }
        }
        return index;
    }

    /**
     * 去除为null的ListNode
     * @param lists
     * @param min
     */
    public ListNode[] filterNullLists(ListNode[] lists, int min) {
        if (null == lists[min]) {
            ListNode[] tempLists = new ListNode[lists.length - 1];
            for (int i = 0; i < lists.length; i++) {
                if (i < min) {
                    tempLists[i] = lists[i];
                } else if (i > min) {
                    tempLists[i - 1] = lists[i];
                }
            }
            return tempLists;
        } else {
            return lists;
        }
    }

}
