package com.ruc.utils_2;

/**
 * @author 俊语
 * @date 2020/10/22 20:47
 */
public class SimulatedCAS_21_2 {
    int count = 0;

    public static void main(String[] args) {
        SimulatedCAS_21_2 simulatedCAS_21_2 = new SimulatedCAS_21_2();
        Thread thread1 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(simulatedCAS_21_2.cas(0, 1));
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(simulatedCAS_21_2.cas(1, 2));
            }
        });
        thread1.start();
        thread2.start();
    }

    synchronized int cas(int expect, int newValue) {
        // 读目前 count 的值
        int curValue = count;
        // 比较目前 count 值是否 == 期望值
        if (curValue == expect) {
            // 如果是，则更新 count 的值
            count = newValue;
        }
        // 返回写入前的值
        return curValue;
    }
}
