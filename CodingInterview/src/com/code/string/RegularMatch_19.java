package com.code.string;

// 面试题19：正则表达式匹配
// 题目：请实现一个函数用来匹配包含'.'和'*'的正则表达式。模式中的字符'.'
// 表示任意一个字符，而'*'表示它前面的字符可以出现任意次（含0次）。在本题
// 中，匹配是指字符串的所有字符匹配整个模式。例如，字符串"aaa"与模式"a.a"
// 和"ab*ac*a"匹配，但与"aa.a"及"ab*a"均不匹配。
public class RegularMatch_19 {

    public boolean match(char[] str,char[] pattern){
        //非法输入
        if(str==null || str.length==0 || pattern==null || pattern.length==0){
            return false;
        }
        return matchCore(str,0,pattern,0);
    }

    private boolean matchCore(char[] str, int strIndex, char[] pattern, int patternIndex) {
        //字符串和模式都操作完毕,返回true
        if(str.length>=strIndex && pattern.length>=patternIndex){
            return true;
        }
        if(str.length<strIndex&&pattern.length>=patternIndex){
            //字符串没有操作完毕，模式操作完毕，返回false
            return false;
        }

        if(str.length>=strIndex&&pattern.length<patternIndex){
            //字符串操作完毕，模式没有操作完毕
            if(patternIndex+1<pattern.length&&pattern[patternIndex+1]=='*'){
                return matchCore(str,strIndex,pattern,patternIndex+2);
            }else {
                return false;
            }
        }

        //字符串没有操作完毕，模式没有操作完毕
            //下一个模式是*
            if(patternIndex+1<pattern.length && pattern[patternIndex+1]=='*'){
                if(str[strIndex]==pattern[patternIndex]){//当前可以匹配
                    return matchCore(str,strIndex,pattern,patternIndex+2)
                            || matchCore(str,strIndex+1,pattern,patternIndex+2)
                            || matchCore(str,strIndex+1,pattern,patternIndex);
                }else {
                    return matchCore(str, strIndex, pattern, patternIndex+2);
                }
            }else {//下一个模式是.
                if(str[strIndex]==pattern[patternIndex]||pattern[patternIndex]=='.'){
                    return matchCore(str,strIndex+1,pattern,patternIndex+1);
                }else {
                    return false;
                }
            }
    }

    public static void main(String[] args) {
        RegularMatch_19 test = new RegularMatch_19();
        String str = "aaa";
        String pattern = "aab*a";

        boolean result = test.match(str.toCharArray(), pattern.toCharArray());
        System.out.println(result);
    }
}
