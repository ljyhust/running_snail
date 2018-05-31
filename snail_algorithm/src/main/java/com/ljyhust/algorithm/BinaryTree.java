package com.ljyhust.algorithm;

import java.util.ArrayList;

/**
 *
 * Created by Administrator on 2018/5/30.
 */
public class BinaryTree {

    /**
     * 层序遍历的省时O(N)方法
     * @param root
     * @return
     */
    public ArrayList<Integer> printFromTopToBottom(TreeNode root) {

        ArrayList<Integer> res = new ArrayList<Integer>();
        if (null == root) {
            return res;
        }
        ArrayList<TreeNode> list = new ArrayList<TreeNode>();
        list.add(root);
        int size = 1;
        TreeNode temp = null;
        for (int i = 0; i < size; i++) {
            temp = list.get(i);
            res.add(temp.val);
            if (null != temp.left) {
                list.add(temp.left);
                size++;
            }
            if (null != temp.right) {
                list.add(temp.right);
                size++;
            }
        }

        return res;
    }
}
