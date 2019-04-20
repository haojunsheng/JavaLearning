package com.code.sortAndFind;

/**
 * 递增排序,旋转数组中的最小值
 */

/**
 * 题目描述：
 * 把一个数组最开始的若干个元素搬到数组的末尾，我们称之为数组的旋转。输入一个递增排序的数组的一个旋转，输出旋转数组的最小元素。例如数组{3,4,5,1,2}为{1,2,3,4,5}的一个旋转，该数组的最小值为1。
 *
 * 解题思路：
 * 1、暴力解法，从头到尾遍历一次，我们就能找出最小的元素，复杂度为O(n)，但是没有利用输入的旋转数组的特性，肯定达不到面试官的要求。
 * 2、二分查找，这里的数组可以看成两个有序的子数组，二分查找对有序数组非常有效，复杂度为O(logn)。
 *
 * 具体分析：
 * 1、先分析数组的特点，这两个有序的数组，前一个数组的所有值都大于等于后一个数组的值，最小值应该出现在后一个数组的第一个元素上。
 * 2、二分查找缩小查找范围依赖于中间值和待查找值的比较。 设置两个指针，index1指向第一个元素，index2指向最后一个元素。 如果中间indexMid指向的元素大于或者等于index1指向的元素，那么，最小的元素一定在indexMid之后。如果indexMid指向的元素小于或者等于index2指向的元素，那么最小元素一定在indexMid之前或者就是indexMid所指向的元素。 就是利用这种判断条件来不断缩小查找范围。
 * 3、结束条件：
 * 最终index1会指向前一个子数组的最后一个元素，index2会指向后一个子数组的第一个元素，两者之间的距离为1。而index2指向的就是最小的元素。
 * 4、特殊情况：
 * 如果被旋转的个数为0，也就是只有一个升序的数组，这时候没有必要去查找，因为第一个元素就是最小的元素。
 * 还有在查找中经常遇到的一种情况，就是有相同元素的情况。 比如 {1, 0, 1, 1, 1} 是{0, 1, 1, 1, 1}的一个旋转。index1、index2、indexMid指向的元素值都为1，这时候不能判断最小值所在的范围，所以需要只能顺序遍历。
 */

/**
 * 测试值一般包含：
 * 功能测试：
 * 边界值测试：
 * 特殊输入测试：空指针。
 */

/**
 * 举一反三：递增排序,旋转数组中的最大值
 * 和上面是差不多的，在正常情况下，是在中间值左边的，同时mid的初始值需要修改为high
 */
public class MinNumberInRotatedArray_11 {

//    public static Integer min(int[] array) {
//        if (array == null || array.length == 0) {
//            return null;
//        }
//
//        int low = 0;
//        int high = array.length - 1;
//        int mid = low; // 这样赋值的原因是：如果将0个元素搬到数组的后面,即旋转后的数组和原数组相同。
//
//        /**
//         * 如果能进入此处循环说明当前还是旋转数组
//         */
//        while (array[low] >= array[high]) {
//            if (high - low == 1) {
//                mid = high;
//                break;
//            }
//
//            mid = (low + high) / 2;
//            // 如果low、high、mid三者指向的数字相等，只能顺序查找
//            // 因为我们无法判断中间数是属于第一个递增子数组还是属于第二个递增子数组
//            // 如原数组为0 1 1 1 1，旋转后为1 0 1 1 1
//            if (array[low] == array[mid] && array[high] == array[mid]) {
//                return minInOrder(array);
//            }
//
//            if (array[mid] >= array[low]) {
//                low = mid;
//            } else if (array[mid] <= array[high]) {
//                high = mid;
//            }
//        }
//        return array[mid];
//    }
//
//    public static int minInOrder(int[] array) {
//        int min = array[0];
//        for (int i = 1; i < array.length; i++) {
//            if (array[i] < min) {
//                min = array[i];
//            }
//        }
//        return min;
//    }

    public static Integer minNumberInRotateArray(int[] array) {
        //处理非法输入
        if(array==null||array.length==0)
            return null;
        int low=0;
        int high=array.length-1;
        int mid=0;//处理并没有发生旋转的情况
        while (array[low]>=array[high]){
            if(high-low==1){
                mid=high;
                break;
            }
            mid=(low+high)/2;
            //特殊情况，顺序比较
            if((array[low]==array[mid])&&(array[mid]==array[high])){
                return minInOrder(array);
            }
            if(array[mid]>=array[low]){
                low=mid;
            }else if(array[mid]<=array[high]){
                high=mid;
            }
        }
        return array[mid];
    }

    private static Integer minInOrder(int[] array) {
        int min = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < min) {
                min = array[i];
            }
        }
        return min;
    }

    public static void main(String[] args) {
        int[] array = {3,4,5,1,2};
        Integer result = minNumberInRotateArray(array);
        System.out.println(result);
    }
}
