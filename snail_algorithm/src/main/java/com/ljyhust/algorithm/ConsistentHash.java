package com.ljyhust.algorithm;

import java.util.LinkedList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * 一致性Hash算法
 * TreeMap用于存储hash与node对应的关系映射
 */
public class ConsistentHash {

    public ConsistentHash() {

    }

    public ConsistentHash(List<String> node) {
        this.realNode = node;
    }

    public ConsistentHash(String mode, int virtualNodeCount, List<String> node) {
        this.MODE = mode;
        this.countVirtualNodePerRealNode = virtualNodeCount;
        this.realNode = node;
    }

    // 存储输入节点
    private List<String> realNode = new LinkedList<>();

    // 存储节点与hash值对应的关系
    private SortedMap<Integer, String> sortedMap = new TreeMap<>();

    // 存储所有虚拟节点
    private List<String> virtualNode = new LinkedList<>();

    // 默认每个实际节点对应5个虚拟节点
    private int countVirtualNodePerRealNode = 5;

    private String MODE = "REAL";

    public SortedMap getNodeCyclicHash() {
        // 模式判断
        if ("REAL".equalsIgnoreCase(MODE)) {
            for (String node : realNode) {
                sortedMap.put(node.hashCode(), node);
            }
        } else if ("VIRTUAL".equalsIgnoreCase(MODE)) {
            for (String node : virtualNode) {
                sortedMap.put(node.hashCode(), node);
            }
        }
        return sortedMap;
    }

    public void enableVirtualNodeMode() {
        this.MODE = "VIRTUAL";
        for (String node : realNode) {
            for (int i = 0; i < countVirtualNodePerRealNode; i++) {
                String nodeName = node + "$$VN" + i;
                virtualNode.add(nodeName);
            }
        }
    }

    /**
     * 通过节点获取最近的服务节点
     * @param visitNode 访问节点
     * @return
     */
    public String getServerNode(String visitNode) {
        int hash = visitNode.hashCode();
        // 获取大于visitNode节点hash值的服务节点，如果没找到则返回整个map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash);
        if (null == subMap || subMap.isEmpty()) {
            return sortedMap.get(sortedMap.firstKey());
        }
        return subMap.get(subMap.firstKey());
    }


}
