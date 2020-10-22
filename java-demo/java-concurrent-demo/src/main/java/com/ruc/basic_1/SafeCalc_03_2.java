package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 11:08
 */
public class SafeCalc_03_2 {
    long value = 0L;

    synchronized long get() {
        return value;
    }

    synchronized void addOne() {
        value += 1;
    }
}