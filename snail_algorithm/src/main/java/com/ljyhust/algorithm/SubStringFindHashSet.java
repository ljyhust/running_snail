package com.ljyhust.algorithm;

import java.util.*;

/**
 * 给定一个字符串 s 和一些长度相同的单词 words。在 s 中找出可以恰好串联 words 中所有单词的子串的起始位置。
 * 注意子串要与 words 中的单词完全匹配，中间不能有其他字符，但不需要考虑 words 中单词串联的顺序。
 * 1.用两个Map去匹配，一个Map记录words中各str-count，另一个Map记录寻找字符串s中word.length*len 的subString找到的str-count
 * 2.比较这两个Map的各个key的value值是否一样，不一样则肯定匹配不上，一样则继续匹配
 * Created by Administrator on 2018/5/9.
 */
public class SubStringFindHashSet {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> indexs = new ArrayList<>();
        // 记录字符串数组words中的所有的word的value
        Map<String, Integer> wordsMap = new HashMap<>();
        for (String word : words) {
            wordsMap.put(word, wordsMap.getOrDefault(word, 0) + 1);
        }

        // 要匹配的字符串的长度strLength
        int strLength = s.length();
        // 数组words数量
        int wordsCount = words.length;
        // 单词长度
        int wordLength = words[0].length();

        for (int i = 0; i <= (strLength - wordLength); i++) {
            Map<String, Integer> findWordsMap = new HashMap<>();
            int j = 0;  //表示找word的位置
            while (j < wordsCount) {
                String word = s.substring(i + j * wordLength, i + (j + 1) * wordLength);
                if (wordsMap.containsKey(word)) {
                    findWordsMap.put(word, findWordsMap.getOrDefault(word, 0) + 1);
                } else {
                    break;
                }

                if (findWordsMap.get(word) > wordsMap.getOrDefault(word, 0)) {
                    break;
                }

                j++;
            }
            if (j == wordsCount) {
                indexs.add(i);
            }
        }
        return indexs;
    }

    public List<Integer> findWordsByPermutation(String s, String[] words) {
        // 先根据words进行全排列
        List<String> permutation = new PermutationString().getStrPermutation(words);
        Set<Integer> indexs = new HashSet<>();
        for (String elem:
             permutation) {
            for(int j = 0; j < s.length(); j++) {
                String substring = s.substring(j);
                if (substring.startsWith(elem)) {
                    indexs.add(j);
                }
            }
        }
        ArrayList<Integer> resList = new ArrayList<>(indexs);
        Collections.sort(resList);
        return resList;
    }

}
