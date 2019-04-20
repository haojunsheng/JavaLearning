package com.code.stack;


import java.util.Stack;

/**
 * 使用两个栈实现队列
 * 相似题目0902 两个队列实现栈
 */

/**
 * 题目描述：
 * 用两个栈实现一个队列。队列的声明如下，请实现它的两个函数appendTail和deletedHead,分别完成在队列尾部插入节点和在队列头部删除节点的功能。
 *
 * 分析：
 * 我们通过一个具体的例子来分析该队列插入和删除元素的过程。
 * 首先插入一个元素a，不妨先把它插入到stack1，此时stack1 中的元素有｛a}，stack2为空。
 * 再压入两个元素b和c,还是插入到stack1中，此时stack1中的元素有｛a,b,c}，其中c位于栈顶，而stack2仍然为空。
 *
 * 这个时候，我们试着从队列中删除一个元素。按照队列先进先出的规则，由于a比b、c先插入到队列中，最先被删除的元素应该是a。元素a存储在stack1中，但并不在栈顶上，因此不能直接被删除。注意到stack2我们还一直没有使用过，现在是让stack2发挥作用的时候了。如果我们把stack1中的元素逐个弹出并压入stack2，元素在stack2中的顺序正好和原来的stack1中的顺序相反。因此经过3次弹出stack1和压入stack2操作之后，stack1为空，而stack2中的元素是｛c,b,a}，这个时候就可以弹出stack2的栈顶a了。此时的stack1为空，而stack2的元素为{c,b}，其中在栈顶，如图所示：
 */
public class QueueWithTwoStacks_09 {

    public static void main(String[] args){
        CQueue<Integer> queue = new CQueue<Integer>();

        //进队列
        for(int i=0; i<5; i++){
            queue.appendTail(i);
        }

        //出队列
        for(int i=0; i<5; i++){
            int temp = queue.deleteHead();
            System.out.print(temp+ "  ");
        }
    }


}

class CQueue<E>{
    private Stack<E> stack1;
    private Stack<E> stack2;

    public CQueue(){
        stack1 = new Stack<E>();
        stack2 = new Stack<E>();
    }

    //在队列尾部插入元素,直接在stack1中插入即可，不用考虑过多
    public void appendTail(E node){
        stack1.push(node);
    }

    //从队列头部删除元素
    public E deleteHead(){
        if(stack2.size() == 0){
            if(stack1.size() == 0){
                throw new RuntimeException();
            }else{
                while(stack1.size() != 0){
                    stack2.push(stack1.pop());
                }
            }
        }
        return stack2.pop ();
    }
}