package com.code.tree;

public class TreeDepth_5501 {
    public class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    /**
     * 二叉树的深度
     * 左子树与右子树较大的深度加1即可
     * @param root
     * @return
     */
//    public int TreeDepth(TreeNode root) {
//        if(root==null)
//            return 0;
//        int left =TreeDepth(root.left);
//        int right=TreeDepth(root.right);
//        return left>right?(left+1):right+1;
//    }













    public int TreeDepth(TreeNode root) {
        if(root==null)
            return 0;
        int left=TreeDepth(root.left);
        int right=TreeDepth(root.right);
        return left>right?(left+1):(right+1);
    }
}
