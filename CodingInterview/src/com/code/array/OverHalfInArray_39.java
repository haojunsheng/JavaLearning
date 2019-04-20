package com.code.array;

public class OverHalfInArray_39 {
    public int MoreThanHalfNum_Solution(int [] array) {
        if(array.length==0)
            return 0;
        if(array.length==1)
            return array[0];
        int count=1;
        int result=array[0];
        for(int i=1;i<array.length;++i){
            if(result!=array[i]){
                count--;
                if(count==0){
                    result=array[i];
                    count=1;
                }
            }else {
                count++;
            }
        }
        count=0;
        for(int i=0;i<array.length;++i){
            if(array[i]==result)
                count++;
        }
        if(count>array.length/2){
            return result;
        }else {
            return 0;
        }
    }
}
