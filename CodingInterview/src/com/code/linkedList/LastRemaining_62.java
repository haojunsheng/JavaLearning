package com.code.linkedList;

import java.util.LinkedList;

public class LastRemaining_62 {
    public int LastRemaining_Solution(int n, int m) {
        LinkedList<Integer> linkList=new LinkedList<Integer>();
        for(int i=0;i<n;++i){
            linkList.add(i);
        }
        int bt=0;
        while(linkList.size()>1){
            bt=(bt+m-1)%linkList.size();
            linkList.remove(bt);
        }
        return linkList.size()==1?linkList.get(0):-1;
    }
}
