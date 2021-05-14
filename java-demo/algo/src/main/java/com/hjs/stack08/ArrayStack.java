package com.hjs.stack08;

/**
 * 基于数组实现的顺序栈
 *
 * @author haojunsheng
 * @date 2021/5/11 20:25
 */
public class ArrayStack {
    private String[] items;
    // 栈中元素个数
    private int count;
    //栈的大小
    private int n;

    public ArrayStack(int n) {
        this.items = new String[n];
        this.n = n;
        this.count = 0;
    }

    // 入栈操作
    public boolean push(String item) {
        // 数组空间不够了，直接返回false，入栈失败
        if (count >= n) {
            return false;
        }
        // 将item放到下标为count的位置，并且count加1
        items[count++] = item;
        return true;
    }

    // 出栈操作
    public String pop() {
        // 栈为空，则直接返回null
        if (count <= 0) {
            return "";
        }
        // 返回下标为count-1的数组元素，并且栈中元素个数count减1
        String tmp = items[--count];
        return tmp;
    }
}
