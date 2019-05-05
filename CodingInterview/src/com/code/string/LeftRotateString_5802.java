package com.code.string;

/**
 *汇编语言中有一种移位指令叫做循环左移（ROL），现在有个简单的任务，就是用字符串模拟这个指令的运算结果。对于一个给定的字符序列S，请你把其循环左移K位后的序列输出。例如，字符序列S=”abcXYZdef”,要求输出循环左移3位后的结果，即“XYZdefabc”。
 *
 * 思路：三次翻转字符串
 * 前n个翻转，后length-n个翻转，整体在翻转一次即可。
 */
public class LeftRotateString_5802 {
    public String LeftRotateString(String str,int n) {
        if(str==null||str.length()<=0||n<=0||n>=str.length())//非法输入
            return str;
        char[] ch =str.toCharArray();
        Reverse(ch,0,n-1);//前n个字符
        Reverse(ch,n,str.length()-1);//后length-n个字符
        Reverse(ch,0,str.length()-1);//整体翻转
        return new String(ch);
    }

    private  void Reverse(char[] ch,int low,int high){
        while(low<high){
            char temp=ch[low];
            ch[low]=ch[high];
            ch[high]=temp;
            low++;
            high--;
        }
    }
}
