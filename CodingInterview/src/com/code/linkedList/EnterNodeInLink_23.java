package com.code.linkedList;

/**
 * 题目描述：
 * 如果一个链表中包含环，如何找出环的入口节点？
 *
 * 分析：
 * 可以用两个指针来解决这个问题。先定义两个指针P1和P2指向链表的头结点。如果链表中环有n个结点，指针P1在链表上向前移动n步，然后两个指针以相同的速度向前移动。当第二个指针指向环的入口结点时，第一个指针已经围绕着环走了一圈又回到了入口结点。
 * 　　剩下的问题就是如何得到环中结点的数目。我们可以使用一快一慢的两个指针。如果两个指针相遇，表明链表中存在环。两个指针相遇的结点一定是在环中。可以从这个结点出发，一边继续向前移动一边计数，当再次回到这个结点时就可以得到环中结点数了。
 */

/**
 * 思路：
 * 1.判断是否存在环，并找到快慢两个指针相遇的位置
 * 2.根据找到的这个相遇位置，统计环中节点的数目n，先让快指针走n步，然后快慢两个指针一起运动，快慢指针相遇时的节点就是环的入口节点
 */
public class EnterNodeInLink_23 {
    // 定义链表结构
    static class ListNode {
        int value;
        ListNode next;
    }

    public ListNode getEnterNode(ListNode head) {
        if(head==null){
            return null;
        }
        //找到相遇的节点
        ListNode meetNode=meetNode(head);
        if(meetNode==null){
            return null;
        }
        //寻找环中节点的个数
        int length=1;
        ListNode temp=meetNode.next;
        while (meetNode!=temp){
            length++;
            temp=temp.next;
        }

        //设置两个指针，其中一个指针先走length步，在同时走，相遇处即入口
        ListNode behind=head;
        ListNode aHead=head;
        while (length>0){
            aHead=aHead.next;
            length--;
        }
        while (aHead!=behind){
            aHead=aHead.next;
            behind=behind.next;
        }
        return aHead;
    }

    /**
     * 找到相遇的节点
     * @param head
     * @return
     */
    private ListNode meetNode(ListNode head) {
        ListNode behind=head.next;//在后面的节点
        if(behind==null){
            return null;//只有一个节点，不存在环
        }
        ListNode aHead=behind.next;//在前面的节点
        while (behind!=null&&aHead!=null){
            if(aHead==behind){//相遇
                return aHead;
            }
            behind=behind.next;
            aHead=aHead.next;
            if(aHead!=null){
                aHead=aHead.next;//走两步
            }
        }
        return null;
    }

}
