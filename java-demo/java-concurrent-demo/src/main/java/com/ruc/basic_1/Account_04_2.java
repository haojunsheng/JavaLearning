package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 11:20
 */
public class Account_04_2 {
    private int balance;

    //转账
    void transfer(Account_04_2 target, int amt) {
        synchronized (Account_04_2.class) {
            if (this.balance > amt) {
                this.balance -= amt;
                target.balance += amt;
            }
        }
    }
}