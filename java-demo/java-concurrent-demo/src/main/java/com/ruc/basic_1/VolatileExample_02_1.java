package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 10:43
 */
public class VolatileExample_02_1 {

    int x = 0;
    volatile boolean v = false;

    public void writer() {
        x = 42;
        v = true;
    }

    public void reader() {
        if (v == true) {
            // 这里 x 会是多少呢?
            System.out.println(x);
        }
    }
}