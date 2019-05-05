package com.code.string;

/**
 * 题目描述
 * 在一个字符串(0<=字符串长度<=10000，全部由字母组成)中找到第一个只出现一次的字符,并返回它的位置, 如果没有则返回 -1（需要区分大小写）.
 */
public class FirstNotRepeatingChar_50 {
    public static int FirstNotRepeatingChar(String str) {
        if(str.length()<=0||str==null)
            return -1;
        int[] num = new int[256];
        char[] chars=str.toCharArray();
        for(int i=0;i<chars.length;++i){
            num[chars[i]]++;
        }
        for(int i=0;i<chars.length;++i){
            if(num[chars[i]]==1)
                return i;
        }
        return -1;
    }

    public static void main(String args[]){
        String test1="";//非法输入测试
        System.out.println("test1:"+FirstNotRepeatingChar(test1));
        String test2="google";//存在只出现一次的字符
        System.out.println("test2:"+FirstNotRepeatingChar(test2));
        String test3="goog";//不存在只出现一次的字符
        System.out.println("test3:"+FirstNotRepeatingChar(test3));
    }
}

