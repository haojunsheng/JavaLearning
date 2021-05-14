package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * 单链表反转
 *
 * @author haojunsheng
 * @date 2021/5/11 17:20
 */
public class ReverseList {
    public ListNode reverse(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }
        ListNode cur = reverse(head.next);
        head.next.next = head;
        head.next = null;
        return cur;
    }


    public String printList(ListNode head) {
        StringBuilder stringBuilder = new StringBuilder();
        while (head != null) {
            stringBuilder.append(head.val);
            head = head.next;
        }
        return stringBuilder.toString();
    }

    public ListNode reverse1(ListNode list) {
        ListNode curr = list, pre = null;
        while (curr != null) {
            ListNode next = curr.next;
            curr.next = pre;
            pre = curr;
            curr = next;
        }
        return pre;
    }
}
