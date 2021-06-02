package com.hjs.sort11;

/**
 * @author 俊语
 * @date 2021/5/25 下午11:22
 */
public class InsertionSort {

    // 插入排序，a表示数组，n表示数组大小
    public void insertionSort(int[] a, int n) {
        if (n <= 1) {
            return;
        }

        for (int i = 1; i < n; ++i) {
            int value = a[i];
            int j = i - 1;
            // 查找插入的位置
            for (; j >= 0; --j) {
                if (a[j] > value) {
                    a[j+1] = a[j];  // 数据移动
                } else {
                    break;
                }
            }
            a[j+1] = value; // 插入数据
        }
    }
}
