package com.ljyhust;

import com.ljyhust.algorithm.ListAlgorithmLearningMergeKList;
import com.ljyhust.algorithm.ListNode;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.tomcat.util.security.MD5Encoder;
import org.junit.Test;
import org.springframework.util.Base64Utils;
import sun.security.provider.MD5;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2018/5/8.
 */
public class TestMain {
    @Test
    public void testNode() {
        ListNode listNode1 = new ListNode(1);
        ListNode listNode2 = new ListNode(1);
        ListNode listNode3 = new ListNode(2);

        listNode1.next = new ListNode(4);
        listNode1.next.next = new ListNode(5);

        listNode2.next = new ListNode(3);
        listNode2.next.next = new ListNode(4);

        listNode3.next = new ListNode(6);

        ListNode[] listNodes = new ListNode[3];
        listNodes[0] = listNode1;
        listNodes[1] = listNode2;
        listNodes[2] = listNode3;
        new ListAlgorithmLearningMergeKList().mergeKLists(listNodes);
    }

    @Test
    public void testInsertSort() {
        int [] a = {1, 1};
        System.err.println(insertSort(a));
    }

    public int[] insertSort(int[] a) {
        for (int i = 1; i < a.length; i++) {
            int temp = a[i];
            int j = 0;
            for (j = i; j > 0 && temp < a[j - 1]; j--) {
                a[j] = a[j - 1];
            }
            a[j] = temp;
        }
        return a;
    }

    @Test
    public void testMain() {
        Integer integer = new Integer(1000091642);
        System.err.println(integer);
    }

    @Test
    public void testExcel() throws FileNotFoundException {
        String filePath = "E://data/test_files/test123.xlsx";
        FileInputStream inputStream = new FileInputStream(filePath);

    }

    @Test
    public void testCalendarCompare() {
        Calendar calStart = Calendar.getInstance();
        calStart.set(2018, 8, 29);
        Calendar calEnd = Calendar.getInstance();
        calEnd.set(2018, 9, 7);
        Calendar current = Calendar.getInstance();
        if (org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(calStart, current, Calendar.DAY_OF_MONTH) <= 0
                && org.apache.commons.lang3.time.DateUtils.truncatedCompareTo(calEnd, current, Calendar.DAY_OF_MONTH) >= 0) {
            System.err.println("判断true");
        } else {
            System.err.println("判断false");
        }
    }

    @Test
    public void testMd5() {
        // 时间戳
        long millis = System.currentTimeMillis();
        String appId = "1810240001";
        String secretKey = "lUgOztz4OCLTUnv7yeEw4exZajdzQHiOLXwX";
        String md5Hex = DigestUtils.md5Hex("appid=" + appId + "&" + "mills=" + millis + "&" + "key=" + secretKey);
        System.err.println("millis=" + millis + ", sign=" + md5Hex);
    }

    @Test
    public void testStrFormat() {
        String format = "当前%s米";
        String res = String.format(format, 100);
        System.err.println(">>>> 结果： " + res);
    }
}
