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

    public void quickSort(int A[],int left,int right){
        //非法输入
        if(A==null||A.length==0||left<0||right>A.length-1||left>=right)
            return;
        int pos=partition(A,left,right);
        quickSort(A,left,pos-1);
        quickSort(A,pos+1,right);
    }

    public int partition(int A[],int left,int right){
        //int temp=A[left];//最好不要使用这种算法，基准元要随机选择
        int p=left+(int)Math.random()*(right-left+1);
        swap(A,left,p);
        int temp=A[left];
        while (left<right){//只要两个数不相遇
            while (left<right&&A[right]>temp)//从右边找小于temp的值
                right--;
            A[left]=A[right];//找到则交换
            while (left<right&&A[left]<=temp)//从左边找大于temp的值
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
    public static void main(String args[]){
        System.out.println(fabonacci(10));
    }
}
