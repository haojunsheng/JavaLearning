package com.code.array;

import java.util.ArrayList;
import java.util.Stack;

public class PrintMatrix_29 {
    private static ArrayList<Integer> printMatrixClockwisely(int nums[][],int rows,int columns){
        ArrayList<Integer> result = new ArrayList<Integer>() ;
        if(nums==null || rows<=0 || columns<=0)//不合法输入
            return result;
        int start=0;

        //结束条件
        while(start*2<rows && start*2<columns){//左上角的横纵坐标相等
            //打印每一圈
            printMatrixInCircle(nums, rows, columns, start,result);
            start++;
        }
        return result;
    }

    private static void printMatrixInCircle(int[][] nums, int rows, int columns, int start,ArrayList<Integer> result) {
        int endx=columns-1-start;
        int endy=rows-1-start;
        //从左到右打印
        for(int i=start;i<=endx;++i){
            result.add(nums[start][i]);
        }
        //从上向下打印
        if(endy>start){
            for (int i=start+1;i<=endy;++i)
                result.add(nums[i][endx]);
        }
        //从右向左打印
        if(endy>start && endx>start){
            for(int i=endx-1;i>=start;--i)
                result.add(nums[endy][i]);
        }
        //从下向上打印
        if(endy-1>start && endx>start){
            for(int i=endy-1;i>=start+1;--i)
                result.add(nums[i][start]);
        }
    }

    public static void main(String args[]){
        int a[][]={{1},{2},{3},{4},{5}};
        ArrayList<Integer> arrayList=printMatrixClockwisely(a,5,1);
        for(int i=0;i<arrayList.size();i++){
            System.out.print(arrayList.get(i));
        }
    }



}
