package com.code.tree;

import java.util.ArrayList;

/**
 * // 面试题40：最小的k个数
 * // 题目：输入n个整数，找出其中最小的k个数。例如输入4、5、1、6、2、7、3、8
 * // 这8个数字，则最小的4个数字是1、2、3、4。
 */
public class KLeastNumbers_40 {

    public ArrayList<Integer> GetLeastNumbers_Solution(int [] input, int k) {
        ArrayList<Integer> list=new ArrayList<Integer>();
        if(input==null||input.length==0||k<=0||k>input.length)//处理非法输入
            return list;
        int right=input.length-1;
        int index=partition(input,0,right);
        while (index!=k-1){
            if(index>k-1)
                index=partition(input,0,index-1);
            else
                index=partition(input,index+1,right);
        }
        for(int i=0;i<k;++i){
            list.add(input[i]);
        }
        return list;
    }

    public int partition(int A[],int left,int right){
        //int temp=A[left];//最好不要使用这种算法，基准元要随机选择
        int p=left+(int)Math.random()*(right-left+1);
        swap(A,left,p);
        int temp=A[left];
        while (left<right){//只要两个数不相遇
            while (left<right && A[right]>temp)//从右边找小于temp的值
                right--;
            A[left]=A[right];//找到则交换
            while (left<right && A[left]<=temp)//从左边找大于temp的值
                left++;
            A[right]=A[left];
        }
        A[left]=temp;
        return left;
    }

    private void swap(int A[],int a,int b) {
        int temp = A[a];
        A[a] = A[b];
        A[b] = temp;
    }


}
