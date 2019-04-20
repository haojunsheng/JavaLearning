package com.code.highQuality;

/**
 * 题目描述：
 * 实现函数double Power(double base,int exponent),求base的exponent次方。不得使用库函数，同时不需要考虑大数问题。
 *
 * 数值的整数次方
 */

import java.math.BigInteger;

/**
 * 我们知道当指数为负数的时候，可以先对指数求绝对值，然后算出次方的结果之后再取倒数。既然有求倒数，我们很自然的就要想到有没有可能对0求倒数，如果对0求倒数怎么办？当底数base是零且指数是负数的时候，我们不做特殊的处理，就会发现对0求倒数从而导致程序运行出错。怎么告诉函数的调用者出现了这种错误？我们使用全局变量的方式处理。
 * 最后需要指出的是，由于0的0次方在数学上没有意义的，因此无论是输出0还是1都是可以接收的，但这都需要和面试官说清楚，表明我们已经考虑到了这个边界值了。
 */
public class Power_16 {

    boolean invalidInput = false;       //使用全局变量处理错误

    public double power(double base, int exponent){
        if(equal(base, 0.0) && exponent <= 0){//异常情况，底数为0且，指数小于等于0
            invalidInput = true;
            return 0.0;
        }

        int absExponent = exponent;         //定义正指数
        if(exponent < 0)
            absExponent = -exponent;

        double result = powerWithExponet(base, absExponent);
        if(exponent < 0)
            result = 1.0/result;

        return result;
    }

    public double powerWithExponet(double base, int exponent){
        double result = 1.0;
        for (int i = 1; i <= exponent; i++) {
            result *= base;
        }
        return result;
    }

    /**
     * 如果输入的指数exponent为32，我们在函数powerWithExponent的循环中需要做31次乘方。
     * 但我们可以换一种思路考虑：我们的目标是求出一个数字的32次方，如果我们已经知道了它的16次方，那么只要16次放的基础上再平方一次就可以了。
     * 而16次方又是8次方的平方。
     * 这样以此类推，我们求32次方只需要5次乘方：先求平方，在平方的基础上求4次方，在4次方的基础上求8次方，在8次方的基础上求16次方，最后在16此方的基础上求32次方。
     *
     * 我们用右移运算代替除2，用位与运算符代替了求余运算符（%)来判断一个数是奇数还是偶数。
     * 位运算的效率比乘除法及求余运算的效率要高很多。
     * @param base
     * @param exponent
     * @return
     */
    //计算指数为非负数的次方
    public double powerWithUnsignedExponet(double base, int exponent){
        if(exponent == 0)
            return 1.0;
        if(exponent == 1)
            return base;

        //使用左移运算符，将exponent除以2
        double result = powerWithUnsignedExponet(base, exponent >> 1);
        result *= result;
        if((exponent & 1) == 1){        //判断是否为奇数
            result *= base;
        }
        return result;
    }

    //比较两个小数是否相等

    /**
     * 由于计算机表示小数（包括float和double型小数）都会有误差，我们不能直接用等号（==）判断两个小数是否相等。如果两个小数的差的绝对值很小，比如小于0.0000001，就可以认为他们相等。
     * @param num1
     * @param num2
     * @return
     */
    public boolean equal(double num1, double num2){
        double tmp = num1 - num2;
        if(tmp > -0.0000001 && tmp < 0.0000001)
            return true;
        else
            return false;
    }

    public static void main(String[] args) {
        Power_16 test = new Power_16();
        double result = test.power(2.0, 4);
        System.out.println("是否是非法输入：" + test.invalidInput);
        System.out.println("结果是：" + result);

    }

}