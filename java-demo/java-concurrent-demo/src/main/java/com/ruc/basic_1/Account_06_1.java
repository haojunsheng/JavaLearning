package com.ruc.basic_1;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊语
 * @date 2020/10/22 11:20
 */
public class Account_06_1 {
    // actr 应该为单例
    private Allocator actr;
    private int balance;

    //转账
    void transfer(Account_06_1 target, int amt) {
        // 一次性申请转出账户和转入账户，直到成功
        while (!actr.apply(this, target)) {
            ;
        }
        try {
            // 锁定转出账户
            synchronized (this) {
                // 锁定转入账户
                synchronized (target) {
                    if (this.balance > amt) {
                        this.balance -= amt;
                        target.balance += amt;
                    }
                }
            }
        } finally {
            actr.free(this, target);
        }
    }
}

class Allocator1 {
    private List<Object> als = new ArrayList<>();

    // 一次性申请所有资源
    synchronized boolean apply(Object from, Object to) {
        while (als.contains(from) || als.contains(to)) {
            try {
                wait();
            } catch (Exception e) {
            }
        }
        als.add(from);
        als.add(to);
        return true;
    }

    // 归还资源
    synchronized void free(Object from, Object to) {
        als.remove(from);
        als.remove(to);
        //notify() 是会随机地通知等待队列中的一个线程，而 notifyAll() 会通知等 待队列中的所有线程
        notifyAll();
    }
}