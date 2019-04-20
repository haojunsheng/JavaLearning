package com.code.tree;

/**
 * 题目描述：
 * 输入某二叉树的前序遍历和中序遍历的结果，请重新构造出该二叉树。假设输入的前序遍历和中序遍历的结果中不包含重复的数字。例如输入的前序遍历序列为｛1，2，4，7，3，5，6，8｝和中序遍历为{4,7,2,1,5,3,6,8},则重建出二叉树并输出它的头结点。
 *
 * 在二叉树的前序遍历序列中，第一个数字总是树的根节点的值。但在中序遍历中，根节点的值在序列的中间，左子树的结点的值位于根节点的值的左边，而右子树的结点的值位于根节点的右边。因此我们需要扫描中序遍历序列，才能找到根节点的值。
 *
 * 如图所示，前序遍历序列的第一个数字1就是根节点的值。扫描中序遍历序列，就能确定根节点的值的位置。根据中序遍历的特点，在根节点的值1前面3个数字都是左子树结点的值，位于1后面的数字都是右子树结点的值。
 *
 * 解题思路：
 * 由于中序遍历序列中，有3个数字是左子树结点的值，因此左子树总共有3个左子结点。
 * 同样，在前序遍历的序列中，根节点后面的3个数字就是3个左子树结点的值，再后面的所有数字都是右子树结点的值。
 * 这样我们就在前序遍历和中序遍历两个序列中，分别找到了左右子树对应的子序列。
 * 既然我们已经分别找到了左、右子树的前序遍历序列和中序遍历序列，我们可以用同样的方法分别去构建左右子树。
 * 也就是说，接下来的事情可以用递归的方法去完成。
 */
public class ConstructBinaryTree_07 {
    public static class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
  }
    public static void main(String[] args) {
        // 二叉树的先序序列
        int[] preOrder = { 1, 2, 4, 7, 3, 5, 6, 8 };
        // 二叉树的中序序列
        int[] inOrder = { 4, 7, 2, 1, 5, 3, 8, 6 };
        TreeNode root = reConstructBinaryTree(preOrder, inOrder);
        printPostOrder(root); // 后序打印二叉树
    }

    /**
     * 根据前序和中序遍历序列完成二叉树的重建
     *
     * @param preOrder
     *            前序遍历序列
     * @param inOrder
     *            中序遍历序列
     */
//    public static BinaryTreeNode reconstructe(int[] preOrder, int[] inOrder) {
//        if (preOrder == null || inOrder == null || preOrder.length == 0 || inOrder.length == 0 || preOrder.length != inOrder.length) {
//            return null;
//        }
//
//        // 二叉树的根节点
//        BinaryTreeNode root = new BinaryTreeNode(preOrder[0]);
//        root.setLeft(null);
//        root.setRight(null);
//
//        // 左子树的节点个数
//        int leftNum = 0;
//        for (int i = 0; i < inOrder.length; i++) {
//            if (root.getValue() == inOrder[i]) {
//                break;
//            } else {
//                leftNum++;
//            }
//        }
//        // 右子树的节点个数
//        int rightNum = inOrder.length - 1 - leftNum;
//
//        // 重建左子树
//        if (leftNum > 0) {
//            //左子树的先序序列
//            int[] leftPreOrder = new int[leftNum];
//            //左子树的中序序列
//            int[] leftInOrder = new int[leftNum];
//            for (int i = 0; i < leftNum; i++) {
//                leftPreOrder[i] = preOrder[i + 1];
//                leftInOrder[i] = inOrder[i];
//            }
//            BinaryTreeNode leftRoot = reconstructe(leftPreOrder, leftInOrder); // 递归构建左子树
//            root.setLeft(leftRoot);
//        }
//
//        // 重构右子树
//        if (rightNum > 0) {
//            //右子树的先序序列
//            int[] rightPreOrder = new int[rightNum];
//            //右子树的中序序列
//            int[] rightInOrder = new int[rightNum];
//            for (int i = 0; i < rightNum; i++) {
//                rightPreOrder[i] = preOrder[leftNum + 1 + i];
//                rightInOrder[i] = inOrder[leftNum + 1 + i];
//            }
//            BinaryTreeNode rightRoot = reconstructe(rightPreOrder, rightInOrder); // 递归构建右子树
//            root.setRight(rightRoot);
//        }
//        return root;
//    }

    public static TreeNode reConstructBinaryTree(int [] preOrder,int [] inOrder) {
        if(preOrder==null||preOrder.length==0||inOrder==null||inOrder.length==0)
            return null;
        //根节点
        TreeNode rootNode=new TreeNode(preOrder[0]);
        rootNode.left=null;
        rootNode.right=null;
        //左子树的节点数
        int leftNum=0;
        for(int i=0;i<inOrder.length;++i){
            if(inOrder[i]==rootNode.val){
                break;
            }
            leftNum++;
        }
        //右子树的节点数
        int rightNum=inOrder.length-1-leftNum;

        //重建左子树
        if(leftNum>0){
            //左子树的先序序列
            int leftPreOrder[]=new int[leftNum];
            //左子树的中序序列
            int leftInOrder[]=new int[leftNum];
            for(int i=0;i<leftNum;++i){
                leftPreOrder[i]=preOrder[i+1];
                leftInOrder[i]=inOrder[i];
            }
            //递归构建左子树
            TreeNode left=reConstructBinaryTree(leftPreOrder,leftInOrder);
            rootNode.left=left;
        }
        //重建右子树
        if(rightNum>0){
            //右子树先序序列
            int rightPreOrder[]=new int[rightNum];
            int rightInOrder[]=new int[rightNum];
            for(int i=0;i<rightNum;++i){
                rightPreOrder[i]=preOrder[i+1+leftNum];
                rightInOrder[i]=inOrder[i+1+leftNum];
            }
            TreeNode right=reConstructBinaryTree(rightPreOrder,rightInOrder);
            rootNode.right=right;
        }
        return rootNode;
    }
    /**
     * 后序遍历二叉树（递归实现）
     */
    public static void printPostOrder(TreeNode root) {
        if (root != null) {
            printPostOrder(root.left);
            printPostOrder(root.right);
            System.out.println(root.val);
        }
    }
}