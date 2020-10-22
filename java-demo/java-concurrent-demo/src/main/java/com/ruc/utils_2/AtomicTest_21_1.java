package com.ruc.utils_2;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 俊语
 * @date 2020/10/22 20:40
 */
public class AtomicTest_21_1 {
    AtomicLong count = new AtomicLong(0);

    void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count.getAndIncrement();
        }
    }

    public static void main(String[] args) {
        AtomicTest_21_1 atomicTest211 = new AtomicTest_21_1();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                atomicTest211.add10K();
                System.out.println(atomicTest211.count);
            }
        });
//        Thread thread2 = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                atomicTest211.add10K();
//                System.out.println(atomicTest211.count);
//            }
//        });
        thread1.start();
//        thread2.start();
    }
}
