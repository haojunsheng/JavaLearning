package com.code.tree;

import java.util.*;

public class MirrorTree_27 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

//    private void mirrorRecursively(TreeNode root){
//        if(root==null){//非法输入
//            return;
//        }
//        if(root.left==null && root.right==null){
//            //结束条件，叶子节点
//            return;
//        }
//        TreeNode temp;
//        temp=root.left;
//        root.left=root.right;
//        root.right=temp;
//        if(root.left!=null){
//            mirrorRecursively(root.left);
//        }
//        if(root.right!=null){
//            mirrorRecursively(root.right);
//        }
//    }
//
//    private TreeNode mirror(TreeNode root){
//        if(root==null){
//            return null;
//        }
//        Queue<TreeNode> queue=new LinkedList<TreeNode>();
//        queue.add(root);
//        while (!queue.isEmpty()){
//            TreeNode temp=queue.poll();
//            if(temp.left!=null&&temp.right!=null){
//                swap(temp.left,temp.right);
//                if(temp.left!=null)
//                    queue.add(temp.left);
//                if (temp.right!=null)
//                    queue.add(temp.right);
//            }
//        }
//        return root;
//    }

    private void swap(TreeNode root) {
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
    }
    //递归去实现
    public void Mirror(TreeNode root) {
        if(root==null)//非法输入
        if(root==null)//非法输入
            return;
        if(root.left==null&&root.right==null)
            return;
        TreeNode temp=root.left;
        root.left=root.right;
        root.right=temp;
        Mirror(root.left);
        Mirror(root.right);
    }

    //非递归去实现,层次遍历
    public void MirrorNotRecursively(TreeNode root) {
        if (root == null)//非法输入
            return;
        Queue<TreeNode> queue=new LinkedList<TreeNode>();
        queue.add(root);
        while (!queue.isEmpty()){
            TreeNode temp=queue.poll();
            if(temp.left!=null || temp.right!=null){
                swap(temp);
                if(root.left!=null)
                    queue.add(temp.left);
                if(root.right!=null)
                    queue.add(temp.right);
            }
        }
    }
}
