package com.code.linkedList;

/**
 * 在O(1)时间内删除链表节点
 */

/**
 * 题目一描述：
 * 给定单链表的头指针和一个节点指针，定义一个函数在O(1)时间删除该节点。
 *
 * 分析：
 * 在单链表中删除一个节点，最常规的方法无疑是从链表的头结点开始，顺序遍历查找要删除的节点，并在链表中删除该节点。
 * 比如图a所表示的链表中，我们要删除节点i，可以从链表的头节点a开始顺序遍历，发现节点h的m_PNext指向要删除的节点i，于是我们可疑把节点h的m_PNext指向i的下一个节点即为j。指针调整之后，我们就可以安全地删除节点i并保证链表没有断开。这种思路由于需要顺序查找，时间复杂度自然就是O（n)了。
 *
 * 之所以要从头开始查找，是因为我们需要得到被删除的节点的前面一个节点。在单链表中，节点中没有前一个节点的指针，所以只好从链表的头结点开始顺序查找。
 * 那是不是一定要得到被删除的节点的前一个节点呢？答案是否定的。我们可以很方面地得到要删除的节点的下一个节点。如果我们把下一个节点的内容复制到要删除的节点上覆盖原有的内容，再把下一个节点删除，那是不是就相当于把当前要删除的节点删除了？
 * 我们还是以前面的例子来分析这种思路，我们要删除的节点i，先把i的下一个节点j的内容复制到i，然后把i的指针指向节点j的下一个节点。此时再删除节点j，其效果刚好是把节点i给删除了（图c)
 * 上述问题还有一个问题；如果要删除的节点位于链表的尾部，那么它就没有下一个节点，怎么办？我们让然从链表的头节点开始，顺序遍历得到该节点的前序节点，并完成删除操作。
 * 最后需要注意的是，如果链表中只有一个节点，而我们又要 链表的头节点（也是尾节点），此时我们在删除节点之后，还需要把链表的头节点设置为NULL。
 */
public class DeleteNodeInList_18 {

    static class ListNode {
        int nodeValue;
        ListNode nextNode;
    }

    /**
     * 复制后一个节点的值到当前节点，注意处理只有一个节点和删除尾节点的情况
     * @param head 链表的头结点
     * @param toBeDeleted 待删除的节点
     */
    public void deleteNode(ListNode head, ListNode toBeDeleted) {
        //参数校验
        if(head == null || toBeDeleted == null){
            return ;
        }

        //链表中只有一个节点，那么待删除的节点既是头结点，又是尾结点
        if(head == toBeDeleted && head.nextNode == null){
            head = null;
        }else{
            //待删除的节点是尾节点
            if(toBeDeleted.nextNode == null){
                ListNode temp = head;
                while(temp.nextNode != toBeDeleted){
                    temp = temp.nextNode;
                }
                temp.nextNode = null;
            }else{          //待删除的节点不是尾节点
                toBeDeleted.nodeValue = toBeDeleted.nextNode.nodeValue;
                toBeDeleted.nextNode = toBeDeleted.nextNode.nextNode;
            }
        }
    }

    /**
     * 打印链表
     * @param head 链表的头结点
     */
    public void printList(ListNode head){
        ListNode temp = head;
        while(temp != null){
            System.out.print(temp.nodeValue + "  ");
            temp = temp.nextNode;
        }
    }

    public static void main(String[] args) {
        DeleteNodeInList_18 test = new DeleteNodeInList_18();

        ListNode head = new ListNode();
        ListNode temp1 = new ListNode();
        ListNode temp2 = new ListNode();
        ListNode temp3 = new ListNode();

        head.nodeValue = 1;
        temp1.nodeValue = 2;
        temp2.nodeValue = 3;
        temp3.nodeValue = 4;
        head.nextNode = temp1;
        temp1.nextNode = temp2;
        temp2.nextNode = temp3;
        temp3.nextNode = null;

        System.out.println("删除前的链表：");
        test.printList(head);

        System.out.println("-----------------------");

        test.deleteNode(head, temp3);       //执行删除操作
        System.out.println("删除后的链表：");
        test.printList(head);
    }
}
