package com.code.array;
//num1,num2分别为长度为1的数组。传出参数
//将num1[0],num2[0]设置为返回结果
public class NumbersAppearOnce_5602 {

    public void FindNumsAppearOnce(int [] array,int num1[] , int num2[]) {
        num1[0]=0;
        num2[0]=0;
        if(array==null || array.length<=1){
            return;
        }
        int result=array[0];
        //将所有数进异或
        for(int i=1;i<array.length;++i){
            result ^= array[i];
        }
        int index=0;//找到result第一个为1的位置(从低位到高位)
        for(index=0;index<32;++index){
            if((result & (1<<index))!=0){
                break;
            }
        }
        //把数组分为两个子数组，标准：数组中的元素的index位为1或者0
        for(int i=0;i<array.length;++i){
            if((array[i]&(1<<index))!=0){
                num1[0]^=array[i];
            }else {
                num2[0]^=array[i];
            }
        }
    }
}
