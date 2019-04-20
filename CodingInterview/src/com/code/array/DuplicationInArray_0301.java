package com.code.array;

//==================================================================
// 《剑指Offer——名企面试官精讲典型编程题》代码
// 作者：何海涛
//==================================================================

// 面试题3（一）：找出数组中重复的数字
// 题目：在一个长度为n的数组里的所有数字都在0到n-1的范围内。数组中某些数字是重复的，但不知道有几个数字重复了，
// 也不知道每个数字重复了几次。请找出数组中任意一个重复的数字。例如，如果输入长度为7的数组{2, 3, 1, 0, 2, 5, 3}，
// 那么对应的输出是重复的数字2或者3。

public class DuplicationInArray_0301 {

    public static int duplication;

// 参数:
//        numbers:     一个整数数组
//        length:      数组的长度
//        duplication: (输出) 数组中的一个重复的数字
// 返回值:
//        true  - 输入有效，并且数组中存在重复的数字
//        false - 输入无效，或者数组中没有重复的数字

//第一种解决方案是排序，时间复杂度是O(nlogn)
//第二中解决方案是使用Hash表,时间复杂度是O(n),空间复杂度是O(n)
// 下面是第三中解决方案
//现在让我们重排这个数组。从头到尾扫描这个数组中的每个数字。当扫描到下标为i的数字的时候，首先比较这个数字(用m表示）是不是i。如果是，接着扫描下一个数字。如果不是，再拿它和第m个数字进行比较。如果它和第m个数字相等，就找到一个重复的数字（该数字在下标为i和m的位置都出现了）。如果它和第m个数字不想等，就把第i个数字和第m个数字交换，把m放到属于它的位置。接下来再重复这个比较，交换的过程，直到发现一个重复的数字。

    // 以数组｛2，3，1，0，2，5，3｝为例来分析找到重复数字的步骤。数组的第0个数字（从0开始计数，和数组的下标保持一致）是2，与它的下标不相等，于是把它和下标为2的数字1交换，交换后的数组是｛1，3，2，0，2，5，3｝。此时第0 个数字是1，仍然与它的下标不相等，继续把它和下标为1的数字3交换，得到数组｛0，1，2，3，2，5，3｝。此时第0 个数字为0，接着扫描下一个数字，在接下来的几个数字中，下标为1，2，3的三个数字分别为1，2，3，他们的下标和数值都分别相等，因此不需要做任何操作。接下来扫描下标为4的数字2.由于它的值与它的下标不相等，再比较它和下标为2的数字。注意到此时数组中下标为2的数字也是2，也就是数字2和下标为2和下标4的两个位置都出现了，因此找到一个重复的数字。
//    public static boolean duplicate(int[] arr) {
//        // 1. 首先判断数组是否为空或者长度是否为0
//        if (arr == null || arr.length == 0) {
//            return false;
//        }
//        // 2. 判断数据的值是否符合要求
//        for (int i = 0; i < arr.length; i++) {
//            if (arr[i] < 0 || arr[i] >= arr.length) {
//                return false;
//            }
//        }
//        //
//        for (int i = 0; i < arr.length; i++) {
//            while (arr[i] != i) {
//                if (arr[i] == arr[arr[i]]) {
//                    duplication = arr[i];
//                    System.out.println(arr[i]);
//                    return true;
//                }
//
//                int temp = arr[i];
//                arr[i] = arr[temp];
//                arr[temp] = temp;
//            }
//        }
//        return false;
//    }


    public static boolean duplicate(int numbers[],int length,int [] duplication) {
        if(numbers==null || numbers.length==0)
            return false;
        for(int i=0;i < numbers.length;++i){
            if(numbers[i]<0||numbers[i]>=numbers.length){
                return false;
            }
        }
        for(int i=0;i<numbers.length;++i){
            while (numbers[i]!=i){
                if(numbers[i]==numbers[numbers[i]]){
                    duplication[0]=numbers[i];
                    return true;
                }
                int temp=numbers[i];
                numbers[i]=numbers[temp];
                numbers[temp]=temp;
            }
        }
        return false;
    }
    public static void main(String[] args) {
        int[] arr = {0, 3, 1, 4, 2, 5, 7 };
        int[] a={};
        boolean result = duplicate(arr,7,a);
        System.out.println(result);
    }

}