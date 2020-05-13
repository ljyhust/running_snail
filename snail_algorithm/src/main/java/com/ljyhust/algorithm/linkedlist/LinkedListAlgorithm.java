package com.ljyhust.algorithm.linkedlist;

import com.ljyhust.algorithm.ListNode;

/**
 * Created by lijinyang on 2020/3/23.
 */
public class LinkedListAlgorithm {

    /**
     * 将给定的单链表L： L 0→L 1→…→L n-1→L n,
     * 重新排序为： L 0→L n →L 1→L n-1→L 2→L n-2→…
     */
    static class ListReorderSolution {

        public void reorderList(ListNode head) {
            if (null == head || null == head.next || null == head.next.next) {
                return;
            }
            // 获取链表中值
            ListNode middleNode = getMiddleNode(head);
            // 反转右侧链表
            middleNode = reverseListNode(middleNode);
            // 合并两子链表
            mergeListNode(head, middleNode);
        }

        public ListNode getMiddleNode(ListNode head) {
            ListNode point = head;
            ListNode endNode = head.next.next;
            while (null != endNode && null != endNode.next) {
                point = point.next;
                endNode = endNode.next.next;
            }
            ListNode middleNode = point.next;
            point.next = null;
            return middleNode;
        }

        /**
         * 反转链表
         * @param head
         * @return
         */
        public ListNode reverseListNode(ListNode head) {
            ListNode resList = head;
            ListNode point = head.next;
            resList.next = null;
            ListNode tempNode = null;
            while(null != point) {
                tempNode = point;
                point = point.next;
                tempNode.next = resList;
                resList = tempNode;
            }
            return resList;
        }

        public void mergeListNode(ListNode head, ListNode rightListNode) {
            ListNode point = head;
            ListNode rightPos = rightListNode;
            ListNode tempNode = null;
            while (null != rightPos) {
                tempNode = rightPos;
                rightPos = rightPos.next;
                tempNode.next = point.next;
                point.next = tempNode;

                point = tempNode.next;
                if (null == point) {
                    point = tempNode;
                }
            }
        }
    }

    public static void main(String[] args) {
        ListNode node1 = new ListNode(1);
        ListNode node2 = new ListNode(2);
        ListNode node3 = new ListNode(3);
        node1.next = node2;
        node2.next = node3;

        (new ListReorderSolution()).reorderList(node1);
    }
}
