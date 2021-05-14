package com.hjs.linkedlist06;

import com.hjs.ListNode;

/**
 * 时间复杂度：O(n)
 * 空间复杂度：O(1)
 *
 * @author haojunsheng
 * @date 2021/5/11 15:16
 */
public class PalindromeList {
    public boolean isPalindrome(ListNode head) {
        if (head == null || head.next == null) {
            return true;
        }
        ListNode prev = null;
        ListNode slow = head;
        ListNode fast = head;
        // 翻转前半部分链表
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            ListNode temp = slow.next;
            slow.next = prev;
            prev = slow;
            slow = temp;
        }
        // 处理奇数的情况
        if (fast != null) {
            slow = slow.next;
        }
        // slow指向后半部分链表
        // prev指向反转后的前半部分链表
        while (slow != null) {
            if (slow.val != prev.val) {
                return false;
            }
            slow = slow.next;
            prev = prev.next;
        }
        return true;
    }
}
