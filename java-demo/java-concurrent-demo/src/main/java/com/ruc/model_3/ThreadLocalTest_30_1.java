package com.ruc.model_3;

import java.util.concurrent.atomic.AtomicLong;

/**
 * @author 俊语
 * @date 2020/10/24 00:40
 */
public class ThreadLocalTest_30_1 {
    public static void main(String[] args) {
        System.out.println(ThreadId.get());
        System.out.println(ThreadId.get());
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(ThreadId.get());
            }
        });
        thread.start();
    }

    static class ThreadId {
        static final AtomicLong nextId = new AtomicLong(0);
        // 定义 ThreadLocal 变量
        static final ThreadLocal<Long> tl = ThreadLocal.withInitial(
                () -> nextId.getAndIncrement());

        // 此方法会为每个线程分配一个唯一的 Id
        static long get() {
            return tl.get();
        }
    }
}

