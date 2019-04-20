package com.code.array;

public class ReorderArray_21 {

    /**
     * 双指针法
     * @param a
     */
    private static void recorderOddEven(int a[]){
        if(a.length==0||a==null){
            return;
        }
        int i=0,j=a.length-1;
        while (i<j){
            while (i<j&&(a[i]&0x1)!=0){//直到碰见偶数
                i++;
            }
            while (i<j&&(a[j]&0x1)==0){//直到碰见奇数
                j--;
            }
            if(i<j){
                int temp=a[i];
                a[i]=a[j];
                a[j]=temp;
            }
        }
    }

    public static void main(String args[]){
        int a[]={1,2,3,4,5,6,7};
        test(a);
        int a2[]={};
        test(a2);
    }
    private static void test(int a[]){
        recorderOddEven(a);
        for(int i=0;i<a.length;++i){
            System.out.print(a[i]);
        }
    }
}
