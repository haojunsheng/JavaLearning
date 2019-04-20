package com.code.linkedList;

public class DeleteNodeOfDuplication_1802 {
    static class ListNode {
        int nodeValue;
        ListNode nextNode;
    }
    private ListNode deleteDuplication(ListNode head){
        if(head==null){
            return null;
        }
        ListNode preNode = new ListNode();//保存前一个节点
        ListNode temp=preNode;
        preNode.nextNode=head;
        ListNode nowNode=head;
        while (nowNode!=null){
            //删除重复节点
            if(nowNode.nextNode!=null && nowNode.nodeValue==nowNode.nextNode.nodeValue){
                while (nowNode.nextNode!=null && nowNode.nodeValue==nowNode.nextNode.nodeValue){
                    nowNode=nowNode.nextNode;
                }
                preNode.nextNode=nowNode.nextNode;
            }else {
                preNode.nextNode=nowNode;
            }
            nowNode=nowNode.nextNode;
        }
        return temp.nextNode;
    }
}
