package com.code.tree;

/**
 * 题目描述：
 * 请实现一个函数，用来判断一颗二叉树是不是对称的。注意，如果一个二叉树和它的镜像是一样的，那么它是对称的。
 *
 * 分析：
 * 对于一棵二叉树，从根结点开始遍历，
 * 如果左右子结点有一个为NULL，那么肯定不是对称二叉树；
 * 如果左右子结点均不为空，但不相等，那么肯定不是对称二叉树；
 * 如果左右子结点均不为空且相等，那么
 * 遍历左子树，遍历顺序为：当前结点，左子树，右子树；
 * 遍历右子树，遍历顺序为：当前结点，右子树，左子树；
 * 如果遍历左子树的序列和遍历右子树的序列一样，那么该二叉树为对称的二叉树。（递归实现）
 */
public class SymmetricalBinaryTree_28 {
    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

    public boolean isSymmetrical(TreeNode root) {
        if(root==null)
            return true;
        if(root.left==null && root.right==null)
            return true;
        return isSymmetrical(root,root);
    }

    public boolean isSymmetrical(TreeNode root1,TreeNode root2){
        if(root1==null&&root2==null)
            return true;
        if(root1==null||root2==null)
            return false;
        if(root1.val!=root2.val)
            return false;
        return isSymmetrical(root1.left,root2.right)&&isSymmetrical(root1.right,root2.left);
    }
}
