package com.code.stack;

import java.util.LinkedList;
import java.util.Queue;

public class StackWithTwoQueues_0902 {
    private LinkedList<Integer> queue1;
    private LinkedList<Integer> queue2;
    public StackWithTwoQueues_0902(){
        queue1=new LinkedList<Integer>();
        queue2=new LinkedList<Integer>();
    }

    public void push(int num){
        if(queue1.size() == 0 && queue2.size() == 0){
            queue1.add(num);
        }
        else if(queue1.size() != 0){
            queue1.add(num);
        }
        else {
            queue2.add(num);
        }
    }

    public Integer pop(){
        if(queue1.size()==0 && queue2.size()==0){
            return null;
        }
        int value=0;
        if(queue1.size()>0){
            while (queue1.size()>1){
                queue2.add(queue1.remove());
            }
            value=queue1.pop();
        }
        else if(queue2.size() > 0){
            while(queue2.size() > 1){
                queue1.add(queue2.remove());
            }
            value = queue2.remove();
        }
        return value;
    }
    public static void main(String[] args){
        StackWithTwoQueues_0902 stack = new StackWithTwoQueues_0902();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println(stack.pop());
        System.out.println(stack.pop());
        stack.push(4);
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }
}

