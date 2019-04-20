package com.code.tree;

/**
 * 题目描述：
 * 输入两棵二叉树A和B，判断B是不是A的子结构。
 * 例如：
 * 图中所示的两棵二叉树，由于A中有一部分子树的结构和B 是一样的，因此B是A的子结构。
 */

/**
 * 分析：二叉树的深度优先遍历
 * 要查找树A中是否存在和树B结构一样的子树，
 * 思路：第一步：先在树A中查找与根结点的值一样的结点，这实际就是树的先序遍历，当树A和树B为空时，定义相应的输出。如果树A某一结点的值和树B头结点的值相同，则调用doesTree1HaveTree2，做第二步判断。
 * 第二步是判断树A中以R为根结点的子树是不是和树B具有相同的结构，使用递归的方法考虑：如果结点R和树B的根结点不相同则返回false,如果相同，则递归判断它们的左右结点的值是不是相同。递归终止条件是我们到达树A或者树B的叶结点。
 */
public class IsSubTree_26 {

    class TreeNode{
        int val=0;
        TreeNode left = null;
        TreeNode right = null;
        public TreeNode(int val){
            this.val=val;
        }
    }

//    private boolean isSubTree(TreeNode root1,TreeNode root2){
//        boolean result=false;
//
//        if(root1!=null && root2!=null){//先序遍历树1，看树1中是否有节点和树2的根节点相同
//            if(root1.val==root2.val){//相同，去比较字数
//                result=isTree1HasTree2(root1,root2);
//            }
//            if(result==false)//遍历左子树
//                result=isSubTree(root1.left,root2);
//            if(result==false)//遍历右子树
//                result=isSubTree(root1.right,root2);
//        }
//        return result;
//    }
//
//    private boolean isTree1HasTree2(TreeNode root1, TreeNode root2) {
//        if(root2==null)
//            return true;
//        if(root1==null)
//            return false;
//
//        if(root1.val!=root2.val)
//            return false;
//        return isTree1HasTree2(root1.left,root2.left) && isTree1HasTree2(root1.right,root2.right);
//    }


    public boolean HasSubtree(TreeNode root1,TreeNode root2) {
        boolean result=false;
        if(root1==null||root2==null)
            return result;
        //根节点相同，去寻找结构是否相同
        if(root1.val==root2.val){
            result=isTree1HasTree2(root1,root2);
        }
        //
        if(result==false)
            result=HasSubtree(root1.left,root2);
        if(result==false)
            result=HasSubtree(root1.right,root2);
        return result;
    }

    private boolean isTree1HasTree2(TreeNode root1, TreeNode root2) {
        if(root2==null)
            return true;
        if(root1==null)
            return false;
        if(root1.val != root2.val)
            return false;
        return isTree1HasTree2(root1.left,root2.left)&&isTree1HasTree2(root1.right,root2.right);
    }
}
