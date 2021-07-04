package com.hjs.thinking.in.spring.bean.lifecycle;

import org.springframework.beans.factory.support.DefaultListableBeanFactory;

/**
 * @author haojunsheng
 * @date 2021/6/30 16:12
 */
public class BeanLifecycleDemo {
    public static void main(String[] args) {
        DefaultListableBeanFactory beanFactory = new DefaultListableBeanFactory();
        // 添加 BeanPostProcessor 实现 MyInstantiationAwareBeanPostProcessor
//        beanFactory.addBeanPostProcessor(new MyInstantiationAwareBeanPostProcessor());
    }
}
