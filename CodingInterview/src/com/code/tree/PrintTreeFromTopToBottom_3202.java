package com.code.tree;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 题目一描述：
 * 从上往下打印二叉树的每个节点，同一层的节点按照从左到右的顺序打印。例如输入下图的二叉树，则一次打印出8，6，10，5，7，9，11。 
 */
public class PrintTreeFromTopToBottom_3202 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

    public static void printFromTopToBottom(TreeNode head){
        ArrayList<ArrayList<Integer>> lists=new ArrayList<ArrayList<Integer>>();

        if(head==null)
            return;
        //广度优先搜索，队列保存
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.add(head);
        //下一层需要打印的节点数
        int nextLevel=0;
        //这一层还需要打印的节点数
        int toBePrint=1;
        while (!queue.isEmpty()){
            TreeNode tempNode=queue.poll();
            System.out.print(tempNode.val+" ");
            if(tempNode.left!=null){
                queue.add(tempNode.left);
                nextLevel++;
            }
            if(tempNode.right!=null){
                queue.add(tempNode.right);
                nextLevel++;
            }
            toBePrint--;
            if(toBePrint==0){
                System.out.print("\n");
                toBePrint=nextLevel;
                nextLevel=0;
            }
        }
    }

    public static void main(String args[]){
    }
}
