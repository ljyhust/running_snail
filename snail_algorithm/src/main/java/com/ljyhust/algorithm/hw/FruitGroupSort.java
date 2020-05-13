package com.ljyhust.algorithm.hw;

import java.util.*;

/**
 * 1. 先分组，根据水果字符串分组
 * 2. 每组内进行排序，先根据重量小到大，再根据编号小到大排序
 * Created by lijinyang on 2020/5/13.
 */
public class FruitGroupSort {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        scanner.nextLine();
        List<String> inputStr = new ArrayList<>();
        while (scanner.hasNextLine()){
            String s = scanner.nextLine();
            if (null == s || "".equals(s)) {
                break;
            }
            inputStr.add(s);
        }
        // 分组
        Map<String, List<FruitGroupElem>> map = new LinkedHashMap<>();
        for (String str : inputStr) {
            String[] strArray = str.split(" ");
            List<FruitGroupElem> elems = map.get(strArray[0]);
            if (null == elems) {
                map.put(strArray[0], new ArrayList<>());
            } else {
                elems.add(new FruitGroupElem(
                        strArray[0], Integer.valueOf(strArray[1]), Integer.valueOf(strArray[2])));
            }
        }

        // 遍历，对分组中的元素进行排序
        for (Map.Entry<String, List<FruitGroupElem>> entry : map.entrySet()) {
            List<FruitGroupElem> value = entry.getValue();
            Collections.sort(value, new Comparator<FruitGroupElem>() {
                @Override
                public int compare(FruitGroupElem o1, FruitGroupElem o2) {
                    // 先比较重量
                    int res = o1.weight.compareTo(o2.weight);
                    if (res == 0) {
                        // 再比较编号
                        return o1.num.compareTo(o2.num);
                    }
                    return res;
                }
            });
        }

        // 输出结果
        for (List<FruitGroupElem> fruitGroupElems : map.values()) {
            for (FruitGroupElem elem : fruitGroupElems) {
                System.out.println(elem.fruit + " " + elem.num + " " + elem.weight);
            }
        }


    }

    static class FruitGroupElem {
        String fruit;
        Integer num;
        Integer weight;

        public FruitGroupElem(String fruit, Integer num, Integer weight) {
            this.fruit = fruit;
            this.num = num;
            this.weight = weight;
        }
    }
}
