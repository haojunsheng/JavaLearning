package com.code.array;

/**
 * 题目：
 * 数组A中，除了某一个数字x之外，其他数字都出现了三次，而x出现了一次。请给出最快的方法找到x。
 *
 *如果数组中没有x，那么数组中所有的数字都出现了3次，在二进制上，每位上1的个数肯定也能被3整除。如{1, 5, 1, 5, 1, 5}从二进制上看有：
 *
 * 1：0001
 *
 * 5：0101
 *
 * 1：0001
 *
 * 5：0101
 *
 * 1：0001
 *
 * 5：0101
 *
 * 二进制第0位上有6个1，第2位上有3个1.第1位和第3位上都是0个1，每一位上的统计结果都可以被3整除。而再对该数组添加任何一个数，如果这个数在二进制的某位上为1都将导致该位上1的个数不能被3整除。因此通过统计二进制上每位1的个数就可以推断出x在该位置上是0还是1了，这样就能计算出x了。
 */
public class NumbersAppearOnce_5602 {

    public int FindNumsAppearOnce(int [] array) {
        int length=array.length;
        if(array==null || length%3!=1)
            return 0;
        //存储每位的和
        int bits[]=new int[32];
        for(int i=0;i<length;++i){
            for(int j=0;j<32;j++){
                bits[j]+=((array[i]>>>j)&1);
            }
        }
        //计算在该位上，如果该位对3取余等于0，那么唯一的那个数在该位为0，否则为1
        int res = 0;
        for(int i = 0; i < 32; i++){
            if(bits[i] % 3 !=0){
                res = res | (1 << i);
            }
        }
        return res;
    }
}
