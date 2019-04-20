package com.code.sortAndFind;

/**
 * 斐波那契数列是递归和循环算法的代表，好好理解吃透。
 *
 * 写一个函数，输入n，求斐波那契数列的第n项。
 * 如果此题采用递归，会造成大量重复性的计算，浪费空间和时间
 * 可以使用循环进行解决，时间复杂度为O(n)
 */
public class Fibonacci_10 {
    public static int fabonacci(int n){
        if(n==0){
            return 0;
        }else if(n==1){
            return 1;
        }
        int result=0;
        int temp1=0,temp2=1;
        for(int i=2;i<=n;++i){
            result=temp1+temp2;
            temp1=temp2;
            temp2=result;
        }
        return result;
    }

    public static void main(String args[]){
        System.out.println(fabonacci(10));
    }
}
