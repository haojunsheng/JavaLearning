package com.code.stack;

import java.util.Stack;

/**
 * 题目描述：
 * 输入两个整数序列，第一个序列表示栈的压入顺序，请判断第二个序列是否为该栈的弹出顺序。假设压入栈的所有数字均不相等。例如序列1，2，3，4，5是某栈的压栈序列，序列，4，5，3，2，1是该压栈序列对应的一个弹出序列，但4，3，5，1，2就不可能是该压栈序列的弹出序列。
 *
 * 分析：
 * 解决这个问题很直观的想法就是建立一个辅助栈，把输入的第一个序列中的数字依次压入该辅助栈，并按照第二个序列的顺序依次从该栈中弹出数字。
 * 以弹出序列4，5，3，2，1为例分析压栈和弹出的过程。第一个希望被弹出的数字是4，因此4需要先压入到辅助栈里面。压入栈的顺序由压栈序列确定了，也就是在把4压入进栈之前，数字1，2，3都需要先压入到栈里面。此时栈里面包含4个数字，分别是1，2，3，4，其中4位于栈顶。把4弹出栈后，剩下的三个数字是1，2，3。接下来希望被弹出的数字是5，由于它不是栈顶的数字，因此我们接着在第一个序列中把4以后的数字压入辅助栈中，直到压入了数字5。这个时候5位于栈顶，就可以被弹出来了。接下来希望被弹出的三个数字依次是3，2，1.由于每次操作前他们都位于栈顶，因此直接弹出即可。下表描述了本例中入栈和出栈的过程。
 */
public class StackSequence_31 {

    public static boolean IsPopOrder(int [] pushA,int [] popA) {
        //处理非法数据
        if(pushA==null||popA==null||pushA.length==0||popA.length==0)
            return false;

        if(pushA.length!=popA.length){
            return false;
        }

        //辅助栈
        Stack<Integer> tempStack=new Stack<Integer>();
        //弹出数组的索引
        int j=0;
        for(int i=0;i<pushA.length;i++){
            tempStack.push(pushA[i]);
            while (!tempStack.isEmpty() && tempStack.peek()==popA[j]){
                tempStack.pop();
                j++;
            }
        }
        //如果最后数组中没有元素，则符合题意
        if(tempStack.isEmpty())
            return true;
        else
            return false;
    }

    public static void main(String args[]){
        //编写测试用例
        //正常
        int[] pushA={1,2,3,4,5};
        int[] popA={4,5,3,2,1};
        System.out.println(IsPopOrder(pushA,popA));
        //错误
        int[] pushA1={1,2,3,4,5};
        int[] popA1={4,5,3,1,2};
        System.out.println(IsPopOrder(pushA1,popA1));
        //非法输入1
        int[] pushA2={};
        int[] popA2={4,5,3,1,2};
        System.out.println(IsPopOrder(pushA2,popA2));
        //非法输入2
        int[] pushA3={1,2};
        int[] popA3={4,5,3,1,2};
        System.out.println(IsPopOrder(pushA3,popA3));
    }
}
