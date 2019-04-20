package com.code.array;

/**
 *面试题42：连续子数组的最大和
 * 题目：输入一个整型数组，数组里有正数也有负数。数组中一个或连续的多个整
 * 数组成一个子数组。求所有子数组的和的最大值。要求时间复杂度为O(n)。
 */
public class GreatestSumOfSubarrays_42 {
    /**
     * 算法时间复杂度O（n）
     * 用total记录累计值，maxSum记录和最大
     * 基于思想：对于一个数A，若是A的左边累计数非负，那么加上A能使得值不小于A，认为累计值对
     *           整体和是有贡献的。如果前几项累计值负数，则认为有害于总和，total记录当前值。
     * 此时 若和大于maxSum 则用maxSum记录下来
     * @param array
     * @return
     */
    public int FindGreatestSumOfSubArray(int[] array) {
        if(array.length==0||array==null)
            return 0;//处理非法输入
        int max=array[0];
        int total=array[0];
        for(int i=1;i<array.length;++i){
            //加上当前元素比之前结果大
            if(total>=0){
                total+=array[i];
            }else {//如果前几项累计值负数，则认为有害于总和，total记录当前值。
                total=array[i];
            }
            if(total>max){
                max=total;
            }
        }
        return max;
    }
}
