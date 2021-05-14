package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * 检测环
 * @author haojunsheng
 * @date 2021/5/11 19:46
 */
public class CheckCircle {
    public static boolean checkCircle(ListNode list) {
        if (list == null) {
            return false;
        }

        ListNode fast = list.next;
        ListNode slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;

            if (slow == fast) {
                return true;
            }
        }

        return false;
    }
}
