package com.ruc.basic_1;

/**
 * @author 俊语
 * @date 2020/10/22 11:20
 */
public class Account_04_1 {
    private int balance;

    //转账
    synchronized void transfer(Account_04_1 target, int amt) {
        if (this.balance > amt) {
            this.balance -= amt;
            target.balance += amt;
        }
    }
}