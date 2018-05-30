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

    /**
     * 判断平衡二叉树的方法，如果根节点判断完毕true，则一定要再判断子节点并求&&
     * @param root
     * @return
     */
    public boolean isBalanced_Solution(TreeNode root) {
        //平衡二叉树左右子树的深度差不超过1
        if (null == root) {
            return true;
        }
        boolean res = false;
        if (height(root.left) - height(root.right) > 1
                || height(root.left) - height(root.right) < -1) {
            res = false;
        } else {
            res = true;
        }
        // 父节点判断成功后要判断子节点
        if (res) {
            res = isBalanced_Solution(root.left) && isBalanced_Solution(root.right);
        }
        return res;
    }

    public int height(TreeNode root) {
        if (null == root) {
            return -1;
        }
        return height(root.left) + 1 > height(root.right) + 1 ?
                height(root.left) + 1 : height(root.right) + 1;
    }

    /**
     * 判断root2是否为root1的子树
     * @param root1
     * @param root2
     * @return
     */
    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        if (null == root2 || null == root1){
            return false;
        }
        if (root1 == root2) {
            return true;
        }
        boolean subLeftRes = false;
        boolean subRightRes = false;
        boolean res = false;
        if (root1.val == root2.val) {
            if (null != root2.left) {
                subLeftRes = HasSubtree(root1.left, root2.left);
            } else {
                subLeftRes = true;
            }

            if (null != root2.right) {
                subRightRes = HasSubtree(root1.right, root2.right);
            } else {
                subRightRes = true;
            }
            res = subLeftRes && subRightRes;
        }
        // 父节点判断不成功，仍然继续判断子节点；注意父节点与子节点相等的情况
        if (!res) {
            return HasSubtree(root1.left, root2) || HasSubtree(root1.right, root2);
        }

        return res;
    }
}
