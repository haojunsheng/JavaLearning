package com.code.tree;

public class TreeDepth_5502 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }
    private boolean isBalanced=true;

    /**
     * 二叉树的深度
     * 左子树与右子树较大的深度加1即可
     * @param root
     * @return
     */
    public boolean IsBalanced_Solution(TreeNode root) {
        if(root==null)
            return false;
        getDepth(root);
        return isBalanced;
    }
    //后续遍历即可
    public int getDepth(TreeNode root){
        if(root==null)
            return 0;
        int left=getDepth(root.left);
        int right=getDepth(root.right);
        if(Math.abs(left-right)>1)
            isBalanced=false;
        return left>right?(left+1):(right+1);
    }
}
