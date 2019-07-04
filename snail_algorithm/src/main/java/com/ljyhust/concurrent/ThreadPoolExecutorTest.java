package com.ljyhust.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

public class ThreadPoolExecutorTest {

    private final AtomicInteger ctl = new AtomicInteger(ctlOf(RUNNING, 0));
    private static final int COUNT_BITS = Integer.SIZE - 3;
    private static final int CAPACITY   = (1 << COUNT_BITS) - 1;

    // runState is stored in the high-order bits
    private static final int RUNNING    = -1 << COUNT_BITS;
    private static final int SHUTDOWN   =  0 << COUNT_BITS;
    private static final int STOP       =  1 << COUNT_BITS;
    private static final int TIDYING    =  2 << COUNT_BITS;
    private static final int TERMINATED =  3 << COUNT_BITS;

    // Packing and unpacking ctl
    private static int runStateOf(int c)     { return c & ~CAPACITY; }
    private static int workerCountOf(int c)  { return c & CAPACITY; }
    private static int ctlOf(int rs, int wc) { return rs | wc; }

    @Test
    public void testCtl() {
        System.err.println(COUNT_BITS + ":" + Integer.toBinaryString(COUNT_BITS));
        System.err.println("CAPACITY:" + CAPACITY + ":" + Integer.toBinaryString(CAPACITY));
        System.err.println("RUNNING:" + Integer.toBinaryString(RUNNING));
        System.err.println("高3位111为RUNNING状态码，低29位全为0");
        System.err.println("ctl.value:" + Integer.toBinaryString(ctl.get()));
        System.err.println("RUNNING:" + RUNNING);
        System.err.print("STOP:" + STOP);
    }

}
