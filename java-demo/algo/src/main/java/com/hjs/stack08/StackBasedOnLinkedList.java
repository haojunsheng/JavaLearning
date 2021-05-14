package com.hjs.stack08;

import com.hjs.ListNode;

/**
 * 基于链表实现的栈。
 * @author haojunsheng
 * @date 2021/5/12 14:09
 */
public class StackBasedOnLinkedList {
    private ListNode top = null;

    public void push(int value) {
        ListNode newNode = new ListNode(value);
        // 判断是否栈空
        if (top == null) {
            top = newNode;
        } else {
            newNode.next = top;
            top = newNode;
        }
    }

    /**
     * 我用-1表示栈中没有数据。
     */
    public int pop() {
        if (top == null) {
            return -1;
        }
        int value = top.val;
        top = top.next;
        return value;
    }

    public void printAll() {
        ListNode p = top;
        while (p != null) {
            System.out.print(p.val + " ");
            p = p.next;
        }
        System.out.println();
    }
}
