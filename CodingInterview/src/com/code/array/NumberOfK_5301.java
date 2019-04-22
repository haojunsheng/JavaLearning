package com.code.array;

/**
 * 数字在排序数组中出现的次数
 * 由于数组有序，所以使用二分查找方法定位k的第一次出现位置和最后一次出现位置
 */
public class NumberOfK_5301 {

    public int GetNumberOfK(int [] array , int k) {
        int num=0;
        int length=array.length;
        if(array!=null&&length>0){
            //第一个k的位置
            int left=getFirstK(array,k,0,length-1);
            int right=getLastK(array,k,0,length-1);
            if(right>-1&&left>-1)
                num=right-left+1;
        }
        return num;
    }

    /**
     * 最后一个k的位置
     */
    private int getLastK(int[] array, int k, int left, int right) {
        if(left>right){
            return -1;
        }
        int middleIndex=(left+right)/2;
        int middleData=array[middleIndex];
        if(k==middleData){//中间位置等于k，说明最后一个k的位置可能在右边
            //中间的元素是最后一个k
            if(middleIndex<(array.length-1)&&(array[middleIndex+1]!=k)||middleIndex==(array.length-1)){
                return middleIndex;
            }else {
                left=middleIndex+1;//k在右半部分
            }
        }else if(middleData>k){
            right=middleIndex-1;//k在右半部分
        }else {
            left=middleIndex+1;
        }
        return getLastK(array,k,left,right);
    }

    /**
     *
     * @param array
     * @param k
     * @param left
     * @param right
     * @return
     */
    public int getFirstK(int[] array,int k,int left,int right){
        if(left>right){
            return -1;
        }
        int middleIndex=(left+right)/2;
        int middleData=array[middleIndex];
        if(k==middleData){//中间位置等于k，说明第一个k的位置可能在左边
            //中间的元素是第一个k
            if(middleIndex>0&&(array[middleIndex-1]!=k)||middleIndex==0){
                return middleIndex;
            }else {
                right=middleIndex-1;//k在左半部分
            }
        }else if(middleData>k){
            right=middleIndex-1;//k在左半部分
        }else {
            left=middleIndex+1;
        }
        return getFirstK(array,k,left,right);
    }

    public static void main(String[] args){
        NumberOfK_5301 numberOfK5301=new NumberOfK_5301();
        int[] array1={1,2,3,3,3,3,4,5};
        //numberOfK5301.test(numberOfK5301,array1,6);
        int[] array2={3,3,3,3,4,5};
        numberOfK5301.test(numberOfK5301,array2,3);
    }
    public void test(NumberOfK_5301 numberOfK5301,int[] array,int k){
        System.out.println(numberOfK5301.GetNumberOfK(array,k));
    }
}
