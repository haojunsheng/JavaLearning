package com.code.tree;

/**
 * 题目描述：
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。如果是返回true，否则返回false。假设输入的数组的任意两个数字互不相同。
 *
 * 分析：
 * 已知条件：后序序列最后一个值为root；二叉搜索树左子树值都比root小，右子树值都比root大。
 * 1、确定root；
 * 2、遍历序列（除去root结点），找到第一个大于root的位置，则该位置左边为左子树，右边为右子树；
 * 3、遍历右子树，若发现有小于root的值，则直接返回false；
 * 4、分别判断左子树和右子树是否仍是二叉搜索树（即递归步骤1、2、3）。
 */


public class SquenceOfBST_33 {
//    public static boolean verifySquenceOfBST(int[] sequence){
//        if(sequence.length==0)
//            return false;
//        if(sequence.length==1)
//            return true;
//        return judge(sequence,0,sequence.length-1);
//    }
//
//
//    private static boolean judge(int[] sequence, int start, int end) {
//        if(start>=end)
//            return true;
//        int i=start;
//        while (sequence[i]<sequence[end]){
//            i++;
//        }
//        for(int j=i;j<end;++j){
//            if(sequence[j]<sequence[end])
//                return false;
//        }
//        return judge(sequence,start,i-1) && judge(sequence,i,end-1);
//    }


    public static boolean VerifySquenceOfBST(int [] sequence) {
        if(sequence.length==0)
            return false;
        if(sequence.length==1)
            return true;
        return judce(sequence,0,sequence.length-1);
    }

    private static boolean judce(int[] sequence,int start,int end) {
        if(start>=end)
            return true;
        int i=start;
        while (sequence[i]<sequence[end]){
            i++;
        }
        for(int j=i;j<end;++j){
            if(sequence[j]<sequence[end])
                return false;
        }
        return judce(sequence,start,i-1)&&judce(sequence,i,end-1);
    }

    public static void main(String args[]){
        int a[]={};
        System.out.println(VerifySquenceOfBST(a));
        int a1[]={1};
        System.out.println(VerifySquenceOfBST(a1));
        int a2[]={5,7,6,10,9,11,8};
        System.out.println(VerifySquenceOfBST(a2));
        int a3[]={5,7,6,10,8,11,9};
        System.out.println(VerifySquenceOfBST(a3));
    }
}

