package com.code.tree;

public class KthNodeInBST_54 {
    public static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    int count=0;
    //本质上考察中序遍历算法,但是这个题目的要求，并不需要把二叉树全部遍历一遍，找到结果后即可正确返回。至于加该条件，主要是考虑叶节点的情况。
     TreeNode KthNode(TreeNode pRoot, int k) {
        if(pRoot==null||k<=0)
            return null;
        TreeNode temp=null;
        temp=KthNode(pRoot.left,k);
        if(temp!=null)
            return temp;
        if(++count==k)
            return pRoot;
        temp=KthNode(pRoot.right,k);
        if(temp!=null)
            return temp;
        return null;
    }

    public static void main(String[] args){
        TreeNode temp2=new TreeNode(2);
        TreeNode temp3=new TreeNode(3);
        TreeNode temp4=new TreeNode(4);
        TreeNode temp5=new TreeNode(5);
        TreeNode temp6=new TreeNode(6);
        TreeNode temp7=new TreeNode(7);
        TreeNode temp8=new TreeNode(8);

        temp5.left=temp3;
        temp5.right=temp7;
        temp3.left=temp2;
        temp3.right=temp4;
        temp7.left=temp6;
        temp7.right=temp8;
        KthNodeInBST_54 node=new KthNodeInBST_54();
        node.KthNode(temp5,3);
    }
}
