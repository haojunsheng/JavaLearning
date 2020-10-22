package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 11:05
 */
public class SynchronizedExample_03_1 {
    // 修饰非静态方法,锁定当前类的 Class 对象
    synchronized void foo() {
        // 临界区
    }

    // 修饰静态方法，锁定当前实例对象 this
    synchronized static void bar() {
        // 临界区
    }

    // 修饰代码块
    Object obj = new Object();

    void baz() {
        synchronized (obj) {
            // 临界区
        }
    }
}