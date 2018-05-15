package com.ljyhust.algorithm;

import java.util.HashMap;
import java.util.Map;

/**
 * Tries树，用于解决字符串搜索
 * 通过节点路径搜索
 */
public class TriesTree {

    private TriesNode root = new TriesNode();

    public boolean inserTriesNode(String str) {
        String[] arr = splitStr(str);

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
        Map<String, TriesNode> children = new HashMap<>();
    }

}
