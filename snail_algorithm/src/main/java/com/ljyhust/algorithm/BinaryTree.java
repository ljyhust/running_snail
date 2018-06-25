package com.ljyhust.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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
     * 比较两颗树是否对称
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

    /**
     * 查找某个节点的路径
     * @param root
     * @param selected
     * @return
     */
    public List<TreeNode> getPathOfNode(TreeNode root, TreeNode selected) {
        Stack<TreeNode> path = new Stack<>();
        findPath(root, selected.val, path);
        return new ArrayList<>(path);
    }

    boolean isNodeFound = false;
    public void findPath(TreeNode root, int target, Stack<TreeNode> path) {
        if (null == root) {
            return;
        }
        path.push(root);
        if (root.val == target) {
            isNodeFound = true;
            return;
        }
        if (!isNodeFound && null != root.left) {
            findPath(root.left, target, path);
        }
        if (!isNodeFound && null != root.right) {
            findPath(root.right, target, path);
        }

        if (!isNodeFound) {
            path.pop();
        }
    }

    /**
     * 寻找最近公共祖先
     * @param root
     * @param node1
     * @param node2
     * @return
     */
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode node1, TreeNode node2) {
        if (root == null) {
            return null;
        }
        if (node1.val == root.val || node2.val == root.val) {
            return root;
        }

        // 找左子树
        TreeNode left = lowestCommonAncestor(root.left, node1, node2);

        TreeNode right = lowestCommonAncestor(root.right, node1, node2);

        if (null != left && null != right) { //左右子树都找到对应的节点，则root为公共节点
            return root;
        }

        return left == null ? right : left;
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

    @Test
    public void testPath() {
        TreeNode root = new TreeNode(7);
        root.left = new TreeNode(5);
        root.right = new TreeNode(4);

        root.left.left = new TreeNode(11);
        root.left.right = new TreeNode(9);

        //root.right.left = new TreeNode(7);
        root.right.right = new TreeNode(6);
        /*System.err.println(Serialize(root));

        List<String> res = new ArrayList<>();
        printTreeByMidium(root, res);
        System.err.println(res);
        System.err.println(isSymmtric(root));*/
        List<TreeNode> pathOfNode = getPathOfNode(root, new TreeNode(6));
        for (TreeNode elem : pathOfNode) {
            System.err.println(elem.val + ",");
        }
    }

}
