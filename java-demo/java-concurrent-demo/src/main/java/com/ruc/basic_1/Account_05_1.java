package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 11:20
 */
public class Account_05_1 {
    private int balance;

    //转账
    void transfer(Account_05_1 target, int amt) {
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
    }
}