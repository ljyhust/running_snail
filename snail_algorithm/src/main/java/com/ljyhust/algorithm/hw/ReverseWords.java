package com.ljyhust.algorithm.hw;

import java.util.Scanner;

/**
 * 单词倒排
 * Created by lijinyang on 2020/5/13.
 */
public class ReverseWords {

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        String str = scan.nextLine();
        // 以空格拆分字符串
        String[] strArray = str.split(" ");
        // 倒序
        reverseArray(strArray);

        for(String word : strArray) {
            System.out.print(word + " ");
        }
    }

    public static void reverseArray(String[] strArray) {
        int l = 0, r = strArray.length - 1;
        while(l < r) {
            String temp = strArray[l];
            strArray[l] = strArray[r];
            strArray[r] = temp;
            l++;
            r--;
        }
    }
}
