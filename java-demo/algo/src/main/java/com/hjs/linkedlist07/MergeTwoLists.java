package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * @author haojunsheng
 * @date 2021/5/11 19:50
 */
public class MergeTwoLists {
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        ListNode soldier = new ListNode(0); //利用哨兵结点简化实现难度 技巧三
        ListNode p = soldier;

        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                p.next = l1;
                l1 = l1.next;
            } else {
                p.next = l2;
                l2 = l2.next;
            }
            p = p.next;
        }

        if (l1 != null) {
            p.next = l1;
        }
        if (l2 != null) {
            p.next = l2;
        }
        return soldier.next;
    }
}
