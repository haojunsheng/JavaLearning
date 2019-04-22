package com.code.array;

public class MissingNumber_5302 {
    public int getMissingNumber(int[] array){
        int length=array.length;
        if(array==null||length==0){
            return -1;
        }
        int left=0;
        int right=length-1;
        while (left<=right){
            int middle=(left+right)/2;
            if(array[middle]!=middle){
                if(middle==0||array[middle-1]==middle-1)
                    return middle;
                right=middle-1;
            }else {//肯定在右边
                left=middle+1;
            }
        }
        if(left==length)
            return length;
        return -1;
    }
}
