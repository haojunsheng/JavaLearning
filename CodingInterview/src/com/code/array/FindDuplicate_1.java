package com.code.array;

public class FindDuplicate_1 {
    /**
     *
     * @param a 有序数组a
     * @param b 有序数组b
     * @return 重复元素
     */
    public int[] findDuplicate(int a[],int b[]){
        int aLength = a.length;
        int bLength = b.length;
        //根据题目要求，aLength或者bLength只要有一个为0，输出为null
        if(aLength==0 || bLength==0){
            return null;
        }
        //存放相同元素的数组的最大长度
        int length = aLength>bLength?bLength:aLength;
        int temp[]=new int[length];

        int i=0,j=0,k=0;
        while (i < aLength && j < bLength){
            if(a[i] == b[j]){//在该位置，元素相同
                temp[k] = a[i];
                k++;
                i++;
                j++;
            }else if(a[i]>b[j]){
                j++;
            }else {
                i++;
            }
        }
        if(k==0){//无相同元素
            return null;
        }else if(k==length){//其中一个数组是另外一个数组的子集
            return temp;
        } else {//部分相同
            int result[] =new int[k];
            for(int l=0;l<k;l++){
                result[l]=temp[l];
            }
            return result;
        }
    }

    public static void main(String args[]){
        FindDuplicate_1 findDuplicate = new FindDuplicate_1();
        //正常情况下测试
        int a1[]={1,2,3,4};
        int b1[]={2,3,4,5};
        int result1[]=findDuplicate.findDuplicate(a1,b1);
        if(result1==null){
            System.out.print("无相同元素\n");
        }else {
            for(int i=0;i<result1.length;i++){
                System.out.print(result1[i]);
            }
            System.out.print("\n");
        }

        //其中一个为空
        int a2[]={};
        int b2[]={1,2,3};
        int result2[]=findDuplicate.findDuplicate(a2,b2);
        if(result2==null){
            System.out.print("无相同元素\n");
        }else {
            for(int i=0;i<result2.length;i++){
                System.out.print(result2[i]);
            }
            System.out.print("\n");
        }

        //其中一个数组是另外一个数组的子集
        int a3[]={1,2,3,4};
        int b3[]={1,2,3};
        int result3[]=findDuplicate.findDuplicate(a3,b3);
        if(result3==null){
            System.out.print("无相同元素\n");
        }else {
                for(int i=0;i<result3.length;i++){
                    System.out.print(result3[i]);
            }
            System.out.print("\n");
        }
    }
}
