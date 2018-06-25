package com.ljyhust.test;

/**
 * 大数加法。用 单向链表 来表示大的整数，求整数的和。
 例子，输入：
 1->2->5->9
 3->6->8
 输出：1->6->2->7
 输入两个单向链表，输出也是单向链表
 要求，不能使用单向链表以外的数据结构来辅助解题
 * Created by Administrator on 2018/6/20.
 */
public class ByteDanceDemo {

    public ListNode bigNumberPlus(ListNode node1, ListNode node2) {
        ListNode head1 = revertListNode(node1);
        ListNode head2 = revertListNode(node2);
        ListNode resNode = new ListNode(0);

        ListNode point = resNode;
        ListNode point1 = head1;
        ListNode point2 = head2;
        while(null != point1 && null != point2) {
            int tempNum = point1.val + point2.val;
            point.val = tempNum;
            point.next = new ListNode(0);

            if (point1.next == null && point2.next != null) {
                while(point2.next != null) {
                    point.next = new ListNode(point2.next.val);
                    point = point.next;
                    point2 = point2.next;
                }
                break;
            }

            if (point2.next == null && point1.next != null) {
                while(point1.next != null) {
                    point.next = new ListNode(point1.next.val);
                    point = point.next;
                    point1 = point1.next;
                }
                break;
            }

            point1 = point1.next;
            point2 = point2.next;
            point = point.next;
        }

        // 处理resNode链表中大于10的数--进位问题
        ListNode temp = resNode;
        while(temp != null) {
            if (temp.val > 9) {
                temp.val = temp.val - 10;
                if (temp.next == null) {
                    temp.next = new ListNode(1);
                } else {
                    temp.next.val = temp.next.val + 1;
                }
            }
            temp = temp.next;
        }

        return revertListNode(resNode);
    }

    /**
     * 反转链表
     * @param node
     */
    public ListNode revertListNode(ListNode node) {
        if (null == node) {
            return null;
        }

        ListNode head = node;
        ListNode point = node.next;
        while(null != point) {
            ListNode temp = point;
            temp.next = head;
            head = temp;

            point = point.next;
        }
        return head;
    }

    class ListNode {
        int val;
        ListNode next;

        ListNode(int x) {
            this.val = x;
        }
    }
}
