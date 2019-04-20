package com.code.linkedList;

public class KthNodeFromEnd_22 {
    static class ListNode{
        int value;
        ListNode nextNode;
    }

    /**
     *题目描述：
     * 输入一个链表，输出该链表中倒数第k个结点。为了符合大多数人的习惯，本题从1开始计数，即链表的尾结点是倒数第1个结点。例如一个链表有6个结点，从头结点开始它们的值依次是1，2，3，4，5，6。这个链表的倒数第3个结点是值为4的结点。
     *
     * 分析:
     * 为了得到第K个结点，很自然的想法是先走到链表的尾端，再从尾端回溯K步。可是我们从链表结点的定义可以看出本题中的链表是单向链表，单向链表的结点只有从前往后的指针而没有从后往前的指针，因此这种思路行不通。
     * 既然不能从尾节点开始遍历这个链表，我们还是把思路回到头结点上来。假设整个链表有N个结点，那么倒数第K个结点就是从头结点开始的第n-k+1个结点。因此我们只能从头结点开始往后走n-k+1步就可以了。如何得到节点数n？这个不难，只需要从头开始遍历链表，每经过一个结点，计数器加1就行了。
     * 也就是说我们需要遍历链表两次，第一次统计出链表中结点的个数，第二次就能找到倒数第k个结点。但是当我们把这个思路解释给面试官之后，他会告诉我们他期待的解法只需要遍历链表一次。
     * 为了实现只遍历链表一次就能找到倒数第k个结点，我们可以定义两个指针。第一个指针从链表的头指针开始遍历向前走k-1。第二个指针保持不动；从第k步开始，第二个指针也开始从链表的头指针开始遍历。由于两个指针的距离保持在k-1，当第一个（走在前面的）指针到达链表的尾结点时，第二个指针正好是倒数第k个结点。
     * @param head
     * @param k
     * @return
     */
    private ListNode findKthToTail(ListNode head,int k){
        if(head==null || k<=0){
            return null;
        }

        ListNode temp=head;
        for(int i=1;i<k;i++){
            if(temp.nextNode==null){
                return null;
            }
            temp=temp.nextNode;
        }
        while (temp!=null){
            head=head.nextNode;
            temp=temp.nextNode;
        }
        return head;
    }

    /**
     * 求链表的中间节点。
     * 如果是12345，那么中间节点为3，如果是1234，那么中间节点为2或者3均可。
     * 方法是：使用两个指针，第一个指针每次都两步，第二个指针每次走一步。这样，当第一个指针走到末尾的时候，第二个指针恰好在中间。
     * @param head
     * @return
     */
    private ListNode findMiddle(ListNode head){
        if(head==null){
            return null;
        }

        ListNode temp=head;

        while (temp.nextNode.nextNode!=null){
            head=head.nextNode;
            temp=temp.nextNode.nextNode;
        }
        return head;
    }
}
