package com.ljyhust.algorithm.sort;

import lombok.extern.slf4j.Slf4j;

/**
 * 字符串排序
 * Created by lijinyang on 2020/4/22.
 */
@Slf4j
public class StrSort {

    /**
     * 索引计数排序
     */
    static class IndexStrSort {

        static final int R = 256;

        // 字符分组
        // 计算位置
        // 复制数据到中间数组缓存
        public void indexStrSortVariableLength(String[] str) {
            String[] temp = new String[str.length];
            sort(str, temp, 0, str.length - 1, 0);
        }

        /**
         * 递归排序核心算法
         * @param array 字符串
         * @param temp 缓存数据
         * @param lo 起始index
         * @param hi 终止index
         */
        private void sort(String[] array, String[] temp, int lo, int hi, int d) {
            if (lo >= hi) {
                return;
            }
            int[] count = new int[R + 2];
            for(int i = lo; i <= hi; i++) {
                count[charAt(array[i], d) + 2]++;
            }

            // 迭代加，为前面的数据留空
            for(int i = 1; i < R + 2; i++) {
                count[i] += count[i - 1];
            }

            // 排序后占位，++表示已占一位
            for(int i = lo; i <= hi; i++) {
                temp[count[charAt(array[i], d)]++] = array[i];
            }

            for(int i = lo; i <= hi; i++) {
                array[i] = temp[i - lo];
            }

            for (int r = 0; r < R; r++) {
                sort(array, temp, lo + count[r], lo + count[r + 1] - 1, d + 1);
            }
        }

        private int charAt(String str, int index) {
            if (index >= str.length()) {
                return -1;
            }
            return str.charAt(index);
        }
    }

    /**
     * 等长字符串采用低位优先
     */
    static class LSD {

        static final int R = 256;

        /**
         * 等长字符串排序
         * @param array 字符串数据
         * @param w 宽度
         */
        public void sort(String[] array, int w) {
            String[] temp = new String[array.length];

            // 每迭代一个字符，排序一次
            for (int index = w - 1; index >= 0; index--) {
                int[] count = new int[R + 1];

                for(int i = 0; i < array.length; i++) {
                    count[array[i].charAt(index) + 1]++;
                }

                for (int r = 1; r < R + 1; r++) {
                    count[r] += count[r - 1];
                }

                for (int i = 0; i < array.length; i++) {
                    temp[count[array[i].charAt(index)]++] = array[i];
                }

                for(int i = 0; i < array.length; i++) {
                    array[i] = temp[i];
                }
            }

        }
    }

    public static void main(String[] args) {
        String[] array = {"adcc", "dddyo", "wqfoh", "jwoqhg", "ajioh", "jfowh", "zzohiohj"};
        new IndexStrSort().indexStrSortVariableLength(array);
        for (String s : array) {
            log.info("====> {}", s);
        }

        String[] arrayConst = {"abc", "acc", "ccd", "ddi", "djw"};
        new LSD().sort(arrayConst, 3);
        for (String s : arrayConst) {
            log.info("====> {}", s);
        }

    }
}
