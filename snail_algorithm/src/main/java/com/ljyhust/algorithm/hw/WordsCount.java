package com.ljyhust.algorithm.hw;

import java.util.*;

/**
 * Created by lijinyang on 2020/5/13.
 */
public class WordsCount {

    /**
     * a11b2bac3bad3abcd2
     * bbabcdabcdbacbacbacbadbadbadaaaaaaaaaaa
     * 1. 先根据数字拆分字符串
     * 2. 然后根据字符串后面的数字排序
     * 3. 遇到相同字母再根据字典序排序
     * @param args
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String inputStr = scanner.nextLine();
        int temp = 0;
        List<String> strList = new ArrayList<>();
        for(int i = 1; i < inputStr.length(); i++) {
            char c = inputStr.charAt(i);
            char lastC = inputStr.charAt(i - 1);
            if(!(c <= '9' && c >= '0') && lastC <= '9' && lastC >= '0') {
                strList.add(inputStr.substring(temp, i));
                temp = i;
            }
            if (i == inputStr.length() - 1) {
                strList.add(inputStr.substring(temp));
            }
        }

        List<StringInt> objList = new ArrayList<>();
        for(String str : strList) {
            objList.add(getIntLastStr(str));
        }
        // 根据每个字符串的数字排序
        Collections.sort(objList, new Comparator<StringInt>() {
            @Override
            public int compare(StringInt o1, StringInt o2) {
                if (o1.num == o2.num) {
                    return o1.str.compareTo(o2.str);
                }
                return o1.num - o2.num;
            }
        });

        StringBuffer stringBuffer = new StringBuffer();
        // 遍历每个字符串转换成目标串
        for(StringInt elem : objList) {
            for(int k = 0; k < elem.num; k++) {
                stringBuffer.append(elem.str);
            }
        }
        System.out.println(stringBuffer.toString());
    }

    /**
     * 求字符串结尾的数字
     * @param str
     * @return
     */
    public static StringInt getIntLastStr(String str) {
        for(int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (c <= '9' && c >= '0') {
                return new StringInt(str.substring(0, i), Integer.valueOf(str.substring(i)));
            }
        }
        return null;
    }

    static class StringInt {

        public int num;
        public String str;

        public StringInt(String str, int num) {
            this.str = str;
            this.num = num;
        }
    }
}
