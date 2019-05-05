package com.code.linkedList;

/**
 * 题目描述：
 * 输入两个递增排序的链表，合并这两个链表并使新链表中的结点仍然是按照递增排序的。例如输入图中的链表1和链表2，则合并之后的升序链表3所示。
 * 分析：
 * 这是一个经常被各公司采用的面试题。在面试过程中，我们发现应聘者最容易犯两个错误：一是在写代码之前没有对合并的过程想清楚，最终合并出来的链表要么中间断开要么并没有递增排序；二是代码的鲁棒性方面存在问题，程序一旦有特殊的输入（如空链表）就会崩溃。接下来分析如何解决这个问题。
 * 首先分析合并两个链表的过程。我们的分析从合并两个链表的头结点开始。链表1的头结点的值小于链表2的头结点的值，因此链表1的头结点将是合并后链表的头结点，如图所示：
 */
public class MergeList_25 {
    static class ListNode {
        int value;
        ListNode next;
    }

    //采用递归的方法实现

//    private ListNode merge(ListNode list1,ListNode list2){
//        //递归边界
//        if(list1==null)
//            return list2;
//        else if(list2==null)
//            return list1;
//
//        ListNode merge=null;
//        if(list1.value<list2.value){
//            merge=list1;
//            merge.next=merge(list1.next,list2);
//        }else {
//            merge=list2;
//            merge.next=merge(list1,list2.next);
//        }
//        return merge;
//    }


    private ListNode merge(ListNode list1,ListNode list2){
        if(list1==null)
            return list2;
        if(list2==null)
            return list1;
        ListNode merge=null;
        if(list1.value<list2.value){
            merge=list1;
            merge.next=merge(list1.next,list2);
        }else {
            merge=list2;
            merge.next=merge(list1,list2.next);
        }
        return merge;
    }

    //采用循环的方法实现
//    private ListNode merge(ListNode list1,ListNode list2){
//
//        if(list1==null)//处理两种特殊情况
//            return list2;
//        if(list2==null)
//            return list1;
//
//        ListNode merge=null;
//        if(list1.value<list2.value){
//            merge=list1;
//            list1=list1.next;
//        }else {
//            merge=list2;
//            list2=list2.next;
//        }
//        //两个链表都非空
//        ListNode temp=merge;
//        while (list1 != null && list2!=null){
//            if(list1.value<list2.value){
//                temp.next=list1;
//                list1=list1.next;
//            }else {
//                temp.next=list2;
//                list2=list2.next;
//            }
//            temp=temp.next;
//        }
//
//        if(list1!=null){
//            temp.next=list1;
//        }else {
//            temp.next=list2;
//        }
//        return merge;
//    }
}


