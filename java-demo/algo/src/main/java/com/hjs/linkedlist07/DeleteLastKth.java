package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * 删除倒数第K个结点
 * @author haojunsheng
 * @date 2021/5/11 19:52
 */
public class DeleteLastKth {
    public static ListNode deleteLastKth(ListNode list, int k) {
        ListNode fast = list;
        int i = 1;
        while (fast != null && i < k) {
            fast = fast.next;
            ++i;
        }

        if (fast == null) {
            return list;
        }

        ListNode slow = list;
        ListNode prev = null;
        while (fast.next != null) {
            fast = fast.next;
            prev = slow;
            slow = slow.next;
        }

        if (prev == null) {
            list = list.next;
        } else {
            prev.next = prev.next.next;
        }
        return list;
    }
}
