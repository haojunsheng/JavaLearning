package com.code.linkedList;

/**
 * 在一个排序的链表中，存在重复的结点，请删除该链表中重复的结点，重复的结点不保留，返回链表头指针。 例如，链表1->2->3->3->4->4->5 处理后为 1->2->5
 */
public class deleteDuplication {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode deleteDuplication(ListNode pHead)
    {
        if(pHead==null || pHead.next==null)
            return pHead;
        ListNode head=new ListNode(0);
        head.next=pHead;
        ListNode current=pHead;
        ListNode prev=head;
        while(current!=null){
            if(current.next!=null&&current.val==current.next.val){
                while(current.next!=null&&current.val==current.next.val){
                    current=current.next;
                }
                current=current.next;
                prev.next=current;

            }else{
                current=current.next;
                prev=prev.next;
            }
        }
        return head.next;
    }
}
