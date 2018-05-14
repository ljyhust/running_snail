package com.ljyhust;

import com.ljyhust.algorithm.ListAlgorithmLearningMergeKList;
import com.ljyhust.algorithm.ListNode;
import org.junit.Test;

/**
 * Created by Administrator on 2018/5/8.
 */
public class TestMain {
    @Test
    public void testNode() {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(2);

        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(5);

        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);

        listNode3.next = new ListNode(6);

        ListNode[] listNodes = new ListNode[3];
        listNodes[0] = listNode1;
        listNodes[1] = listNode2;
        listNodes[2] = listNode3;
        new ListAlgorithmLearningMergeKList().mergeKLists(listNodes);
    }

}