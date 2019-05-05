package com.code.string;

/**
 * 翻转单词顺序列
 *
 * “student. a am I”。后来才意识到，这家伙原来把句子单词的顺序翻转了，正确的句子应该是“I am a student.”
 *
 * 解决思路是二次翻转，即先整体翻转，在对每个单词进行翻转即可得到正确的序列
 */
public class ReverseWordsInSentence_58 {
    public static String ReverseSentence(String str) {
        String strTemp=str.trim();
        if(strTemp.equals("")||str==null)
            return str;//非法输入
        char[] ch = strTemp.toCharArray();
        Reverse(ch,0,ch.length-1);//整体翻转
        int blank=-1;
        for(int i=0;i<ch.length;++i){
            if(ch[i]==' '){
                int nextBlank=i;
                Reverse(ch,blank+1,nextBlank-1);
                blank=nextBlank;
            }
        }
        //最后一个单词也需要进行翻转
        Reverse(ch,blank+1,ch.length-1);
        return new String(ch);
    }

    private static void Reverse(char[] ch,int low,int high){
        while(low<high){
            char temp=ch[low];
            ch[low]=ch[high];
            ch[high]=temp;
            low++;
            high--;
        }
    }
    public static void main(String args[]){
        String test1="   ";
        System.out.println("test1:"+ReverseSentence(test1));
        String test2="I am a student.";
        System.out.println("test2:"+ReverseSentence(test2));
        String test3="I";
        System.out.println("test3:"+ReverseSentence(test3));
    }
}
