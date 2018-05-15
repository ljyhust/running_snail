package com.ljyhust.algorithm;

/**
 * 左式堆，主要操作为合并操作，较大根值堆与较小根值堆的右子树合并
 * 用链表表示左式堆
 * Created by ljyhust on 2018/5/13.
 */
public class LeftHeapStruct {
    // 根节点
    private Node root;

    /**
     * 合并操作 O(log N)
     * @param other
     */
    public void merge(LeftHeapStruct other) {
        if (this == other) {
            return;
        }
        root = merge(root, other.root);
    }

    public Node merge(Node h1, Node h2) {
        if (null == h1) {
            return h2;
        }
        if (null == h2) {
            return h1;
        }

        if (h1.value < h2.value) {
            return mergeRight(h1, h2);
        } else {
            return mergeRight(h2, h1);
        }
    }

    /**
     * 合并主例程
     * @param h1 根值小的堆
     * @param h2 根值大的堆
     * @return
     */
    private Node mergeRight(Node h1, Node h2) {
        if (null == h1.leftChild) {
            h1.leftChild = h2;
        } else {
            h1.rightChild = merge(h1.rightChild, h2);
            if (h1.leftChild.npl < h1.rightChild.npl) {
                swapChild(h1);
                h1.npl = h1.rightChild.npl + 1;
            }
        }
        return h1;
    }

    private void swapChild(Node h1) {
        if (null == h1) {
            return;
        }
        Node temp = h1.leftChild;
        h1.leftChild = h1.rightChild;
        h1.rightChild = temp;
    }

    class Node {
        private int value;

        Node leftChild;
        Node rightChild;
        // 左式堆的非零距离
        int npl;

        Node(int elem) {
            this.value = elem;
        }

        Node(int elem, Node left, Node right) {
            this.value = elem;
            this.leftChild = left;
            this.rightChild = right;
        }
    }
}
