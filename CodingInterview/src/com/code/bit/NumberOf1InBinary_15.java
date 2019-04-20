package com.code.bit;

/**
 * 题目描述：
 * 请实现一个函数，输入一个整数，输出该数二进制表示中1的个数。例如把9表示成二进制是1001，有2位是1，因此如果输入9，该函数输出2。
 */
public class NumberOf1InBinary_15 {

    /**
     *注意，可以使用位运算的，不要使用加减乘除，因为运算效率低下。
     * 还要注意，能够使用左移的，不要使用右移运算，因为如果负数的右移运算比较麻烦。
     *
     * 为了避免死循环，我们可以不右移输入的数字n。首先把n和1做与运算，判断n的最低位是不是1。
     * 接着把1左移一位得到2，再和n做与运算，就能判断n的次低位是不是1。这样反复左移，每次都能判断n的其中一位是不是1。
     * @param num
     * @return
     */
    public static int numberOf1(int num){
        int count=0;
        int flag=1;
        while (flag!=0){
            if((flag&num)!=0){
                count++;
            }
            flag=flag<<1;
        }
        return count;
    }

    /**
     * 上面的算法本质上需要进行该整数所对应的二进制的位数次运算
     * 下面的算法只需要进行该整数所对应的二进制中1的个数次运算
     *
     *把一个整数减去1，再和原整数做与运算，会把该整数最右边的一个1变成0。
     * 那么一个整数的二进制表示中有多少个1，就可以进行多少次运算。
     * @param num
     * @return
     */
    public static int numberOf1Simple(int num){
        int count=0;
        while (num!=0){
            count++;
            num=(num-1)&num;
        }
        return count;
    }

    public static void main(String args[]){
        System.out.println(numberOf1(9));
        System.out.println(numberOf1Simple(9));
    }
}
