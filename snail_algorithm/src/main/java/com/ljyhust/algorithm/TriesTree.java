package com.ljyhust.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Tries树，用于解决字符串搜索
 * 通过节点路径搜索
 */
public class TriesTree {

    private TriesNode root = new TriesNode();

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
            if (!current.children.containsKey(arr[i])) {
               current.children.put(arr[i], new TriesNode());
               res = true;
            }
            current = current.children.get(arr[i]);
            i++;
        }
        return res;
    }

    /**
     * 搜索前缀串
     * @param str
     * @return
     */
    public String[] searchFontString(String str) {
        String[] arr = splitStr(str);
        int i = 0;
        // 当前节点位置
        TriesNode current = root;
        while (i < arr.length) {
            if (!current.children.containsKey(arr[i])) {
                return null;
            }
            current = current.children.get(arr[i]);
            i++;
        }
        // 深度搜索

    }

    public String[] dst(TriesNode node) {
        if (null == node.children) {
            return null;
        }
        Set<Map.Entry<String, TriesNode>> entries = node.children.entrySet();
        for (Map.Entry<String, TriesNode> elem : entries) {
            dst(elem.getValue());
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
        boolean isTraversed;
        // 子节点，key- 字符；value-节点信息
        Map<String, TriesNode> children = new HashMap<>();
    }

}
