package com.junyu.lesson85;

import org.springframework.context.ApplicationEvent;

/**
 * @author 俊语
 * @date 2021/5/9 下午11:24
 */
public class DemoEvent extends ApplicationEvent {
    private String message;

    public DemoEvent(Object source, String message) {
        super(source);
    }

    public String getMessage() {
        return this.message;
    }
}
