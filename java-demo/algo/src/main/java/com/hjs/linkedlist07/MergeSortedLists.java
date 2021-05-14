package com.hjs.linkedlist07;

import com.hjs.ListNode;

/**
 * 有序链表合并
 * @author haojunsheng
 * @date 2021/5/11 19:47
 */
public class MergeSortedLists {
     public static ListNode mergeSortedLists(ListNode la, ListNode lb) {
     if (la == null) {
         return lb;
     }
     if (lb == null) {
         return la;
     }

     ListNode p = la;
     ListNode q = lb;
     ListNode head;
     if (p.val < q.val) {
       head = p;
       p = p.next;
     } else {
       head = q;
       q = q.next;
     }
     ListNode r = head;

     while (p != null && q != null) {
       if (p.val < q.val) {
         r.next = p;
         p = p.next;
       } else {
         r.next = q;
         q = q.next;
       }
       r = r.next;
     }

     if (p != null) {
       r.next = p;
     } else {
       r.next = q;
     }

     return head;
    }
}
