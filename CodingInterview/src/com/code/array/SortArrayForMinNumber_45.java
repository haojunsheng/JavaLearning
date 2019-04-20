package com.code.array;

import java.util.Arrays;
import java.util.Comparator;

/**
 * 考察了大数问题
 * 解题思路：
 *  * 先将整型数组转换成String数组，然后将String数组排序，最后将排好序的字符串数组拼接出来。关键就是制定排序规则。
 *  * 排序规则如下：
 *  * 若ab > ba 则 a > b，
 *  * 若ab < ba 则 a < b，
 *  * 若ab = ba 则 a = b；
 *  * 解释说明：
 *  * 比如 "3" < "31"但是 "331" > "313"，所以要将二者拼接起来进行比较
 */
public class SortArrayForMinNumber_45 {
    public String PrintMinNumber(int [] numbers) {
        if(numbers==null||numbers.length==0)
            return "";
        int length=numbers.length;
        String[] strings=new String[length];
        StringBuffer buffer=new StringBuffer();
        for(int i=0;i<length;++i){
            strings[i]=String.valueOf(numbers[i]);
        }
        //自定义排序规则
        Arrays.sort(strings, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                String c1=o1+o2;
                String c2=o2+o1;
                return c1.compareTo(c2);
            }
        });
        for(int i=0;i<length;++i){
            buffer.append(strings[i]);
        }
        return buffer.toString();
    }
}
