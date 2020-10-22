package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/21 21:00
 */
public class Test_01_1 {
    private static long count = 0;

    private  void add10K() {
        int idx = 0;
        while (idx++ < 10000) {
            count += 1;
        }
    }

    public static long calc() throws InterruptedException {
        // 创建两个线程，执行add()操作
        Test_01_1 test = new Test_01_1();
        Thread th1 = new Thread(() -> {
            test.add10K();
        });
        Thread th2 = new Thread(() -> {
            test.add10K();
        }); // 启动两个线程
        th1.start();
        th2.start(); // 等待两个线程执行结束
        th1.join();
        th2.join();
        return count;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(Test_01_1.calc());
    }
}
