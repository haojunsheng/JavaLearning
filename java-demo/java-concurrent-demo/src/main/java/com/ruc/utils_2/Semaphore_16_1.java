package com.ruc.utils_2;

import java.util.Queue;

/**
 * @author 俊语
 * @date 2020/10/22 19:05
 */
public class Semaphore_16_1 {
    //计数器
    int count;
    // 等待队列
    Queue queue;

    // 初始化操作
    Semaphore_16_1(int c) {
        this.count = c;
    }

    //也被称为PV原语，semWait() 和 semSignal()
    void acquire() {
        this.count--;
        if (this.count < 0) {
            // 将当前线程插入等待队列
            // 阻塞当前线程
        }
    }

    void release() {
        this.count++;
        if (this.count <= 0) {
            // 移除等待队列中的某个线程 T
            // 唤醒线程 T
        }
    }
}
