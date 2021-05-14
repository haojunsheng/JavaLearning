package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * 求中间结点
 * @author haojunsheng
 * @date 2021/5/11 19:53
 */
public class FindMiddleNode {
    public static ListNode findMiddleNode(ListNode list) {
        if (list == null) {
            return null;
        }

        ListNode fast = list;
        ListNode slow = list;

        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
        }

        return slow;
    }
}
