package com.code.tree;

public class NextNodeInBinaryTrees_08 {

    public BinaryTreeNode getNextNode(BinaryTreeNode pNode) {

        if (pNode == null) {
            return null;
        }
        BinaryTreeNode tempNode = null;

        // 如果该节点有右子节点
        if (pNode.getRight() != null) {
            tempNode = pNode.getRight();
            while (tempNode.getLeft() != null) {
                tempNode = tempNode.getLeft();
            }
            return tempNode;
        }

        // 如果该节点没有右子节点，它是其父节点的左子节点
        if (pNode.getFather() == null)
            return null;
        if (pNode.getFather().getLeft() == pNode) {
            return pNode.getFather();
        }

        // 如果该节点没有右子节点，它是其父节点的右子节点
        tempNode = pNode.getFather();
        while (tempNode.getFather() != null) {
            if(tempNode.getFather().getLeft() == tempNode){
                return tempNode.getFather();
            }
            //继续向上找父节点
            tempNode = tempNode.getFather();
        }
        return null;

    }

    public class TreeLinkNode {
        int val;
        TreeLinkNode left = null;
        TreeLinkNode right = null;
        TreeLinkNode next = null;

        TreeLinkNode(int val) {
            this.val = val;
        }
    }

    public TreeLinkNode GetNext(TreeLinkNode pNode){
        if(pNode==null)
            return null;
        //三种情况
        TreeLinkNode temp=null;
        //1.如果该节点有右子树，那么为右子树的最左节点
        if(pNode.right!=null){
            temp=pNode.right;
            while (temp.left!=null){
                temp=temp.left;
            }
            return temp;
        }
        //2.该节点没有右子树
        //2.1 但是是父节点的左节点
        if(pNode.next==null)
            return null;
        if(pNode.next.left==pNode)
            return pNode.next;
        //2.2 最复杂的情况，是父节点的右节点
        temp=pNode.next;
        while (temp.next!=null){
            if(temp.next.left==temp)
                return temp.next;
            temp=temp.next;
        }
        return null;
    }

}