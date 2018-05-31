package com.ljyhust.algorithm;

import org.junit.Test;

/**
 * 根据前序遍历+中序遍历重建二叉树
 */
public class ReConstructBinaryTree {

    public TreeNode createBinaryTreeByFront(int [] pre, int [] in) {
        // 通过前序和中序可确定根节点和左右子树，然后对左/右子树递归求解
        int rootVal = pre[0];
        TreeNode root = new TreeNode(rootVal);
        int i = 0;
        while(rootVal != in[i]) {
            i++;
        }
        if (i != 0) {
            int [] subLeftPre = new int[i];
            int [] subLeftIn = new int[i];
            for (int m = 0; m < subLeftPre.length; m++) {
                subLeftPre[m] = pre[m + 1];
                subLeftIn[m] = in[m];
            }
            root.left = createBinaryTreeByFront(subLeftPre, subLeftIn);
        }

        if (pre.length - i - 1 != 0) {
            int[] subRightPre = new int[pre.length - i - 1];
            int[] subRightIn = new int[pre.length - i - 1];
            for (int m = 0; m < subRightPre.length; m++) {
                subRightPre[m] = pre[i + m + 1];
                subRightIn[m] = in[i + m + 1];
            }
            root.right = createBinaryTreeByFront(subRightPre, subRightIn);
        }
        return root;
    }


    @Test
    public void testNode() {
        int [] pre = {1,2,3,4,5,6,7};
        int [] in = {3,2,4,1,6,5,7};
        System.err.println(createBinaryTreeByFront(pre, in));
    }


}
