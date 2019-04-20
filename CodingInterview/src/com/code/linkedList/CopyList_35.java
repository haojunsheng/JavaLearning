package com.code.linkedList;

public class CopyList_35 {
    static class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public static RandomListNode Clone(RandomListNode pHead)
    {
        if(pHead==null){//非法输入
            return null;
        }

        //创建复制后的链表
        cloneNodes(pHead);
        //连接复制节点的兄弟节点
        connectSibling(pHead);
        //将原始节点和复制节点分开
        return reconnectNodes(pHead);
    }

    private static RandomListNode reconnectNodes(RandomListNode pHead) {
        RandomListNode clonedHead=pHead.next;
        RandomListNode currentNode=pHead;
        while (currentNode!=null){
            RandomListNode cloneNode=currentNode.next;
            currentNode.next=cloneNode.next;
            cloneNode.next=cloneNode.next==null?null:cloneNode.next.next;
            currentNode=currentNode.next;
        }
        return clonedHead;
    }

    private static void connectSibling(RandomListNode pHead) {
        RandomListNode currentNode=pHead;
        while (currentNode!=null){
            RandomListNode cloneNode=currentNode.next;
            if(currentNode.random!=null)
                cloneNode.random=currentNode.random.next;
            currentNode=cloneNode.next;
        }
    }

    private static void cloneNodes(RandomListNode pHead) {
        RandomListNode currentNode=pHead;
        while (currentNode!=null){
            RandomListNode cloneNode=new RandomListNode(currentNode.label);
            cloneNode.next=currentNode.next;
            currentNode.next=cloneNode;
            currentNode=cloneNode.next;
        }
    }

    public static void main(String args[]){
        RandomListNode head = new RandomListNode(1);
//        RandomListNode node1 = new RandomListNode(2);
//        RandomListNode node2 = new RandomListNode(3);
        head.next = null;
//        node1.next = node2;
        head.random = null;
//        node1.random = node2;
//        node2.random = head;

        Clone(head);
    }
}
