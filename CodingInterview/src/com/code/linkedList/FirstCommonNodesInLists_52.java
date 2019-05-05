package com.code.linkedList;
/**
 * 找出2个链表的长度，然后让长的先走两个链表的长度差，然后再一起走
 * （因为2个链表用公共的尾部）
 *
 * 可以暴力去解，需要时间复杂度为O(mn)
 *
 * 还可以使用栈的特性，思路是倒着找，因为如果有相同的，最后一个肯定是相同的。
 *
 * 最好的思路是一个指针先走，另一个指针后走。
*/
public class FirstCommonNodesInLists_52 {
    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }
    public ListNode FindFirstCommonNode(ListNode pHead1, ListNode pHead2) {
        if(pHead1==null||pHead2==null)
            return null;
        int length1=0,length2=0;
        ListNode temp1=pHead1;
        while(temp1!=null){
            ++length1;
            temp1=temp1.next;
        }
        ListNode temp2=pHead2;
        while(temp2!=null){
            ++length2;
            temp2=temp2.next;
        }
        int length=length1-length2;;
        if(length<0){
            length=-length;
            while(length>0){
                pHead2=pHead2.next;
                length--;
            }
        }
        while(length>0){
            pHead1=pHead1.next;
            length--;
        }
        while(pHead1!=null&&pHead1.val!=pHead2.val){
            pHead1=pHead1.next;
            pHead2=pHead2.next;
        }
        return pHead1;
    }
}
