package com.ljyhust.algorithm;

import org.junit.Test;

import java.util.*;

/**
 * Tries树，用于解决字符串搜索
 * 通过节点路径搜索
 */
public class TriesTree {

    private TriesNode root = new TriesNode(null, null);

    /**
     * 添加字符串
     * @param str 输入
     * @return false-树中已有str，true-树中无str
     */
    public boolean insertTriesNode(String str) {
        String[] arr = splitStr(str);
        int i = 0;
        // 当前节点位置
        TriesNode current = root;
        boolean res = false;
        // 将字符串插入到前缀树中
        while (i < arr.length) {
            if (null == current.children) {
                current.children = new HashMap<String, TriesNode>();
            }
            if (!current.children.containsKey(arr[i])) {
               current.children.put(arr[i], new TriesNode(arr[i], null));
               res = true;
            }
            current = current.children.get(arr[i]);
            i++;
        }
        current.ifWord = true;
        return res;
    }

    /**
     * 搜索前缀串
     * @param str
     * @return
     */
    public List<String> searchFontString(String str) {
        String[] arr = splitStr(str);
        int i = 0;
        // 当前节点位置
        TriesNode current = root;
        while (i < arr.length) {
            if (null == current.children || !current.children.containsKey(arr[i])) {
                return null;
            }
            current = current.children.get(arr[i]);
            i++;
        }
        // 深度搜索
        List<String> list = new ArrayList<>();
        dst(current, str, list);
        return list;
    }

    public void dst(TriesNode node, String preStr, List<String> selectList) {
        if (null != node.children) {
            // 子节点
            List<TriesNode> nodeList = new ArrayList<>();
            Set<Map.Entry<String, TriesNode>> entries = node.children.entrySet();
            for (Map.Entry<String, TriesNode> elem : entries) {
                nodeList.add(elem.getValue());
            }

            for (TriesNode subNode : nodeList) {
                String value = subNode.value;
                String word = preStr + value;
                if (subNode.ifWord) {
                    selectList.add(word);
                }
                dst(subNode, word, selectList);
            }
        }
    }

    /**
     * 拆分字符串
     * @param str 输入
     * @return
     */
    private String[] splitStr(String str) {
        String[] res = new String[str.length()];
        for (int i = 0; i < str.length(); i++) {
            res[i] = str.substring(i, i + 1);
        }
        return res;
    }

    class TriesNode {
        // 标识当前节点是否是一个完整的词
        boolean ifWord = false;
        String value;
        // 子节点，key- 字符；value-节点信息
        Map<String, TriesNode> children;

        TriesNode(String value, Map<String, TriesNode> children) {
            this.value = value;
            this.children = children;
        }
    }

    @Test
    public void testMain() {
        // 构建树
        String[] input = {"算法", "算法导论", "算错了", "数据结构", "面试算法", "计算机", "互联网"};
        TriesTree tree = new TriesTree();
        for (String elem : input) {
            tree.insertTriesNode(elem);
        }

        List<String> list = tree.searchFontString("面试");
        System.err.println(list);

    }

}
