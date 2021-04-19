package com.junyu.lesson56.first;

import java.util.ArrayList;
import java.util.List;

/**
 * @author haojunsheng
 * @date 2021/4/18 23:39
 */
public class ConcreteSubject implements Subject {
    private List<Observer> observers = new ArrayList();

    @Override
    public void registerObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Message message) {
        for (Observer observer : observers) {
            observer.update(message);
        }
    }
}
