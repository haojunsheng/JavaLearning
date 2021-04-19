package com.junyu.lesson56.first;

/**
 * @author haojunsheng
 * @date 2021/4/18 23:38
 */
public interface Subject {
    void registerObserver(Observer observer);

    void removeObserver(Observer observer);

    void notifyObservers(Message message);
}
