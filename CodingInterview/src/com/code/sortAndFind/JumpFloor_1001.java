package com.code.sortAndFind;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * 变态跳台阶
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。求该青蛙跳上一个n级的台阶总共有多少种跳法。
 * 可以使用数学归纳法进行证明f(n)=2^(n-1)
 *
 * 因为n级台阶，第一步有n种跳法：跳1级、跳2级、到跳n级
 * 跳1级，剩下n-1级，则剩下跳法是f(n-1)
 * 跳2级，剩下n-2级，则剩下跳法是f(n-2)
 * 所以f(n)=f(n-1)+f(n-2)+...+f(1)
 * 因为f(n-1)=f(n-2)+f(n-3)+...+f(1)
 * 所以f(n)=2*f(n-1)=2^2 * f(n-2)=2^(n-1) *f(1)=2^(n-1)
 *
 * 另解：每个台阶都有跳与不跳两种情况（除了最后一个台阶），最后一个台阶必须跳。所以共用2^(n-1)中情况
 *
 * 关于2^(n-1)的求解，既可以使用循环的方式进行求解，也可以使用移位的方式进行
 */
public class JumpFloor_1001 {
    public static int JumpFloorII(int target) {
        return 1<<(target-1);
    }

    public static void main(String args[]){
        System.out.print(JumpFloorII(100000));
    }
}
