package com.code.tree;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

/**
 * 题目一描述：
 * 从上往下打印二叉树的每个节点，同一层的节点按照从左到右的顺序打印。例如输入下图的二叉树，则一次打印出8，6，10，5，7，9，11。 
 */
public class PrintTreeFromTopToBottom_3203 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

    public static void printFromTopToBottom(TreeNode head){
        if(head==null)
            return;
        //从左到右，奇数层节点
        Stack<TreeNode> stack1=new Stack<TreeNode>();
        //从右向左，偶数层节点
        Stack<TreeNode> stack2=new Stack<TreeNode>();
        stack1.push(head);
        while (!stack1.isEmpty()|| !stack2.isEmpty()){
            if(!stack1.isEmpty()){
                while (!stack1.isEmpty()){
                    TreeNode tempNode=stack1.pop();
                    System.out.print(tempNode.val+" ");
                    if(tempNode.left!=null){
                        stack2.push(tempNode.left);
                    }
                    if(tempNode.right!=null){
                        stack2.add(tempNode.right);
                    }
                }
                System.out.println();
            }else {
                while (!stack2.isEmpty()){
                    TreeNode tempNode=stack2.pop();
                    System.out.print(tempNode.val+" ");
                    if(tempNode.right!=null){
                        stack1.push(tempNode.right);
                    }
                    if(tempNode.left!=null){
                        stack1.add(tempNode.left);
                    }
                }
                System.out.println();
            }

        }
    }

    public static void main(String args[]){
    }
}
