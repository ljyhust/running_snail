package com.ljyhust.algorithm;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 全排列字符串并打印输出
 * 递归方法
 * Created by Administrator on 2018/5/9.
 */
public class PermutationString {

    public List<String[]> permutation(String[] strings) {
        return permutation(strings, 0);
    }

    public List<String> getStrPermutation(String[] strings) {
        List<String[]> list = permutation(strings);
        List<String> res = new ArrayList<>();
        for (String[] elem : list) {
            StringBuffer strBuffer = new StringBuffer();
            for (String str:
                 elem) {
                strBuffer.append(str);
            }
            res.add(strBuffer.toString());
        }
        return res;
    }

    /**
     * 全排列字符串
     * @param strings 字符串数组
     * @param start 开始串
     * @return
     */
    public List<String[]> permutation(String[] strings, int start) {

        // 基准，如果只有最后一个元素，前面肯定已经排好了
        List<String[]> res = new ArrayList<>();
        if (strings.length - 1 == start) {
            String[] tempStrs = new String[strings.length];
            for (int i = 0; i < strings.length; i++) {
                tempStrs[i] = strings[i];
            }
            res.add(tempStrs);
            return res;
        }

        // 递归
        for (int i = start; i < strings.length; i++) {
            //交换元素，让所有的元素都有机会成为start那个
            swap(strings, i, start);
            // 排列子数组
            res.addAll(permutation(strings, start + 1));
            swap(strings, i, start);
        }
        return res;
    }

    public void swap(String[] chs,int i,int j)
    {
        String temp;
        temp=chs[i];
        chs[i]=chs[j];
        chs[j]=temp;
    }

    @Test
    public void testMain() {
        String[] strings = new String[3];
        strings[0] = "a";
        strings[1] = "b";
        strings[2] = "c";
        for(String[] elem : permutation(strings, 0)) {
            System.err.println(Arrays.toString(elem));
        }
    }
}
