package com.code.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 题目一描述：
 * 从上往下打印二叉树的每个节点，同一层的节点按照从左到右的顺序打印。例如输入下图的二叉树，则一次打印出8，6，10，5，7，9，11。
 */
public class PrintTreeFromTopToBottom_3201 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

    public static ArrayList<Integer> printFromTopToBottom(TreeNode head){
        if(head==null)
            return null;
        //广度优先搜索，队列保存
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.add(head);
        ArrayList<Integer> arrayList=new ArrayList<Integer>();
        while (!queue.isEmpty()){
            TreeNode tempNode=queue.poll();
            arrayList.add(tempNode.val);
            if(tempNode.left!=null){
                queue.add(tempNode.left);
            }
            if(tempNode.right!=null){
                queue.add(tempNode.right);
            }
        }
        return arrayList;
    }

    public static void main(String args[]){
        TreeNode node=null;

    }
}
