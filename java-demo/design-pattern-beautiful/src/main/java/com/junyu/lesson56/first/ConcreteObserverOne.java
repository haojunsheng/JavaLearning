package com.junyu.lesson56.first;

/**
 * @author haojunsheng
 * @date 2021/4/18 23:41
 */
public class ConcreteObserverOne implements Observer {
    @Override
    public void update(Message message) {
        //TODO: 获取消息通知，执行自己的逻辑...
        System.out.println("ConcreteObserverOne is notified.");
    }
}
