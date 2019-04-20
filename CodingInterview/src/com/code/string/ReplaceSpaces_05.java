package com.code.string;

/**
 * 将字符串中的空格替换为%20
 */

/**
 * 在合并两个数组，包括字符串时，如果从前到后进行处理每个数字，
 * 需要重复移动数字时，则可以考虑从后向前进行处理，这样可以减少移动的次数，从而提高效率
 */
public class ReplaceSpaces_05{

    /**
     * 我们先遍历一次字符串，这样就能够统计出字符串中空格的总数，并可以计算出替换之后字符串的总长度。每替换一个空格，长度增加2，因此替换以后的字符串的长度等于原来的长度加上2乘以空格的数目，我们还是以前面的字符串”We are happy”为例，“We are happy”这个字符串的长度是14，里面有两个空格，因此替换之后的字符串的长度为18
     *
     * 我们从字符串的后面开始复制和替换。首先准备两个指针，P1和P2.   P1指向原始字符串的末尾，而P2指向替换之后的字符串的末尾。接下来我们向前移动指针P1,逐个把它指向的字符复制到P2指向的位置，直到碰到第一个空格为止。此时字符串包含如下图b所示，灰色阴影的区域是做了字符拷贝的区域。碰到第一个空格之后，把P1向前移动一格，在P2之前插入字符串”%20“，由于”%20“的长度为3，同时也要把P2向前移动3格如图所示。
     *
     * 我们接着向前复制，直到碰到第二个空格（d)所示。和上一次一样，我们再把P1向前移动1格，并把P2向前移动3格插入”%20“（如图e)，此时P1，P2指向同一个位置，表明所有的空格都已经替换完毕。
     *
     * 从上面的分析我们可以看出，所有的字符都只复制一次，因此这个算法的时间效率是O(n)，比第一个思路要快。
     */
    /**
//     *
//     * @param c
//     *            输入的带空格的字符数组
//     * @param length
//     *            第一个字符到最后一个字符的长度，不是字符数组的长度
//     * @return
//     */
//    public static String replaceSpace(char[] c, int length) {
//        // 查询空格的个数
//        int count = 0;
//        for (int i = 0; i < length; i++) {
//            if (c[i] == ' ') {
//                count++;
//            }
//        }
//        // 重新计算新数组的大小
//        int newLength = length + count * 2;
//        // 从尾到头查找
//        int i = length - 1;
//        int j = newLength - 1;
//        while (i >= 0 && j >= i) {
//            if (c[i] == ' ') {
//                c[j--] = '0';
//                c[j--] = '2';
//                c[j--] = '%';
//            } else {
//                c[j--] = c[i];
//            }
//            i--;
//        }
//        return new String(c);
//    }

    public static String replaceSpace(StringBuffer str) {
        if(str==null||str.length()==0)
            return null;
        int len=str.length();
        int count=0;
        //统计空格的个数
        for(int i=0;i<len;++i){
            if(str.charAt(i)==' '){
                count++;
            }
        }
        int newLen=len+count*2;
        int i=len-1;
        int j=newLen-1;
        StringBuffer stringBuffer=new StringBuffer(newLen);
        for(int k=0;k<newLen;k++){
            stringBuffer.append('0');
        }
        while (i>=0&&j>=i){
            if(str.charAt(i)==' '){
                stringBuffer.setCharAt(j--,'0');
                stringBuffer.setCharAt(j--,'2');
                stringBuffer.setCharAt(j--,'%');
            }else {
                stringBuffer.setCharAt(j--,str.charAt(i));
            }
            i--;
        }
        return stringBuffer.toString();
    }
    public static void main(String[] args) {
        String str = "We are happy.";
        int length = str.length();
        char[] olderArray = str.toCharArray();
        // 为简单起见，我们假设给它一个新的空间，空间的大小足以存下替换后的字符
        char[] newArray = new char[100];
        for (int i = 0; i < olderArray.length; i++) {
            newArray[i] = olderArray[i];
        }
        String resultStr = replaceSpace(new StringBuffer(str));
        System.out.println(resultStr);
    }
}


/**
 *public class Solution {
 *     public String replaceSpace(StringBuffer str) {
 *     	// 查询空格的个数
 *         int count = 0;
 *         int length=str.length();
 *         char
 *         for (int i = 0; i < length; i++) {
 *             if (str.charAt(i) == ' ') {
 *                 count++;
 *             }
 *         }
 *         // 重新计算新数组的大小
 *         int newLength = length + count * 2;
 *         // 从尾到头查找
 *         int i = length - 1;
 *         int j = newLength - 1;
 *         while (i >= 0 && j >= i) {
 *             if (str.charAt(i) == ' ') {
 *                 str.setCharAt(j--,'0');
 *                 str.setCharAt(j--,'2');
 *                 str.setCharAt(j--,'%');
 *             } else {
 *                 str.setCharAt(j--,str.charAt(i));
 *             }
 *             i--;
 *         }
 *         return str.toString();
 *     }
 * }
 */