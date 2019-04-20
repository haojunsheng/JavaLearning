package com.code.tree;

import java.util.List;
import java.util.Stack;

public class BinaryTreeConvert_36 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

    //两个节点，分别记录临时节点和最终头结点
    TreeNode pre=null;
    TreeNode lastLeft=null;
    //递归
//    public TreeNode Convert(TreeNode pRootOfTree) {
//        if(pRootOfTree==null){//判空
//            return null;
//        }
//        Convert(pRootOfTree.left);
//        pRootOfTree.left=pre;
//        if(pre!=null){
//            pre.right=pRootOfTree;
//        }
//        pre=pRootOfTree;
//        lastLeft=(lastLeft==null)?pRootOfTree:lastLeft;
//        Convert(pRootOfTree.right);
//        return lastLeft;
//    }
//
//    //非递归
//    public TreeNode Convert1(TreeNode pRootOfTree) {
//        if (pRootOfTree == null)
//            return pRootOfTree;
//        Stack<TreeNode> stack=new Stack<TreeNode>();
//        TreeNode list=null;//头结点
//        while (pRootOfTree!=null|| !stack.isEmpty()){
//            if(pRootOfTree!=null){
//                stack.push(pRootOfTree);
//                pRootOfTree=pRootOfTree.right;
//            }else {
//                pRootOfTree=stack.pop();
//                if(list==null){
//                    list=pRootOfTree;
//                }else {
//                    list.left=pRootOfTree;
//                    pRootOfTree.right=list;
//                    list=pRootOfTree;
//                }
//                pRootOfTree=pRootOfTree.left;
//            }
//        }
//        return list;
//    }

    //中序遍历
    public TreeNode Convert(TreeNode pRootOfTree) {
        if(pRootOfTree==null)
            return null;
        Convert(pRootOfTree.left);
        pRootOfTree.left=pre;
        if(pre!=null)
            pre.right=pRootOfTree;
        pre=pRootOfTree;
        lastLeft=(lastLeft==null?pRootOfTree:lastLeft);//头结点
        Convert(pRootOfTree.right);
        return lastLeft;
    }

    //非递归
    public TreeNode Convert1(TreeNode pRootOfTree) {
        if(pRootOfTree==null)
            return null;
        Stack<TreeNode> stack=new Stack<TreeNode>();
        TreeNode root=null;
        while (pRootOfTree!=null|| (!stack.isEmpty())){
            if(pRootOfTree!=null){
                stack.push(pRootOfTree);
                pRootOfTree=pRootOfTree.right;
            }else {
                pRootOfTree=stack.pop();
                if(root==null){
                    root=pRootOfTree;
                }else {
                    root.left=pRootOfTree;
                    pRootOfTree.left=root;
                    root=pRootOfTree;
                }
                pRootOfTree=pRootOfTree.left;
            }
        }
        return root;
    }
}
