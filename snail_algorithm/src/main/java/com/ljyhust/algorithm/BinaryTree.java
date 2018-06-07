package com.ljyhust.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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

    /**
     * 中序遍历
     * @param root
     * @return
     */
    public void printTreeByMidium(TreeNode root, List<String> res) {
        if (root == null) {
            return;
        }
        if (null == root.left && null != root.right) {
            res.add("#");
            res.add(root.val + "");
            printTreeByMidium(root.right, res);
        } else if (null != root.left && null == root.right) {
            printTreeByMidium(root.left, res);
            res.add(root.val + "");
            res.add("#");
        } else {
            printTreeByMidium(root.left, res);
            res.add(root.val + "");
            printTreeByMidium(root.right, res);
        }
    }

    /**
     * 二叉树序列化
     * @param root
     * @return
     */
    public String Serialize(TreeNode root) {
        // 用先序遍历
        String res = "";
        if (null == root) {
            res = res + "#,";
            return res;
        }
        res = res + root.val + ",";
        res = res + Serialize(root.left);
        res = res + Serialize(root.right);
        return res;
    }


    /**
     * 判断是否为对称的二叉树，用中序遍历解决
     * @param root
     * @return
     */
    public boolean isSymmtric(TreeNode root) {
        if (null == root) {
            return true;
        }
        ArrayList<String> res = new ArrayList<>();
        printTreeByMidium(root, res);
        // 判断数据奇偶对称性
        int size = res.size();
        if (size % 2 == 0) {
            return false;
        }
        int center = size / 2;
        int i = center;
        int j = center;
        while (i >= 0 && j < size) {
            if (!res.get(i).equals(res.get(j))) {
                return false;
            }
            i--;
            j++;
        }
        return true;
    }

    /**
     * 比较两颗树是否对称
     * @param root1
     * @param root2
     * @return
     */
    public boolean compareTwoTreeNode(TreeNode root1, TreeNode root2) {
        if (null == root1 && null == root2) {
            return true;
        }
        boolean res = false;
        if (null != root1.left && null != root2.right && root1.left.val == root2.right.val) {
            res = true;
        }

        if (null == root1.left && null == root2.right) {
            res = true;
        }

        if (null == root1.right && null == root2.left) {
            res = res && true;
        }

        if (null != root1.right && null != root2.left && root1.right.val == root2.left.val) {
            res = res && true;
        }

        if (res) {
            res = res && compareTwoTreeNode(root1.left, root2.right) && compareTwoTreeNode(root1.right, root2.left);
        }
        return res;
    }

    @Test
    public void testMain() {
        TreeNode root = new TreeNode(5);
        root.left = new TreeNode(5);
        root.right = new TreeNode(5);

        root.left.left = new TreeNode(5);
        root.left.right = new TreeNode(5);

        //root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(5);
        System.err.println(Serialize(root));

        List<String> res = new ArrayList<>();
        printTreeByMidium(root, res);
        System.err.println(res);
        System.err.println(isSymmtric(root));
    }
}
