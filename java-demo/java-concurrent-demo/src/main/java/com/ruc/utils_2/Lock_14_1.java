package com.ruc.utils_2;

import java.util.concurrent.Semaphore;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author 俊语
 * @date 2020/10/22 17:04
 */
public class Lock_14_1 {
    private final Lock rtl = new ReentrantLock();
    int value=0;

    public void addOne() {
        // 获取锁
        rtl.lock();
        try {
            value += 1;
        } finally {
            // 保证锁能释放
            rtl.unlock();
        }
    }

    public static void main(String[] args) {
        Lock_14_1 lock141 = new Lock_14_1();

        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                lock141.addOne();
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(lock141.value);
            }
        });
        thread1.start();
        thread2.start();

    }
}
