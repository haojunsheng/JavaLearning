package com.code.string;

/**
 * 把字符串转换成整数,将一个字符串转换成一个整数(实现Integer.valueOf(string)的功能，但是string不符合数字要求时返回0)，要求不能使用字符串转换整数的库函数。 数值为0或者字符串不是一个合法的数值则返回0。
 *
 * 本题较为简单，难点在于要考虑各种情况。
 *
 * 判断一个数是否会溢出的代码值得留意，比较好。
 *  int sum=result*10+(int)(ch[i]-'0');
 *             if((sum-(int) (ch[i] - '0'))/10!=result){
 *                 flag=true;
 *                 return 0;
 *             }
 */
public class StringToInt_67 {
    public static boolean flag=false;//默认是合法输入
    public int StrToInt(String str) {
        if(str==null||str.length()<=0){
            flag=true;
            return 0;
        }
        int symbol=0;//0表示正数，1表示负数
        int start=0;//0表示第一位不是符号位，1表示第一位是符号位
        char[] ch=str.trim().toCharArray();
        if(ch[0]=='+'){
            start=1;
        }
        if(ch[0]=='-'){
            symbol=1;
            start=1;
        }
        int result=0;
        for(int i=start;i<ch.length;++i){
            if(ch[i]<'0'||ch[i]>'9'){
                flag=true;
                return 0;
            }
            //判断是否会溢出
            int sum=result*10+(int)(ch[i]-'0');
            if((sum-(int) (ch[i] - '0'))/10!=result){
                flag=true;
                return 0;
            }
            result=result*10+(int)(ch[i]-'0');
        }
        if(symbol==1)
            return result*(-1);
        else
            return result;
    }
}
