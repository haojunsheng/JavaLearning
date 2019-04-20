package com.code.array;

public class OverHalfInArray_39 {
    public int MoreThanHalfNum_Solution(int [] array) {
        if(array.length==0)
            return 0;
        if(array.length==1)
            return array[0];
        int count=1;
        int result=array[0];
        for(int i=1;i<array.length;++i){
            if(result!=array[i]){
                count--;
                if(count==0){
                    result=array[i];
                    count=1;
                }
            }else {
                count++;
            }
        }
        count=0;
        for(int i=0;i<array.length;++i){
            if(array[i]==result)
                count++;
        }
        if(count>array.length/2){
            return result;
        }else {
            return 0;
        }
    }

    /**
     *快排的思想
     * 假设你选择的数字p在快排一轮完成后下标是m，那么比p小的数字就有m个，如果k小于m，那么第k大的数字一定在p的左边，否则在右边
     */
    public int MoreThanHalfNum(int [] array) {
        if(array.length==0)
            return 0;
        if(array.length==1)
            return array[0];
        int middle=array.length/2;
        int index=partition(array,0,array.length-1);
        while (index!=middle){
            if(index>middle)
                index=partition(array,0,index-1);
            else
                index=partition(array,index+1,array.length-1);
        }
        int result=array[index];
        int count=0;
        for(int i=0;i<array.length;++i){
            if(array[i]==result)
                count++;
        }
        if(count>array.length/2){
            return result;
        }else {
            return 0;
        }
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
