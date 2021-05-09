package com.junyu.lesson85;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * @author 俊语
 * @date 2021/5/9 下午11:33
 */
// Listener监听者
@Component
public class DemoListener implements ApplicationListener<DemoEvent> {
    @Override
    public void onApplicationEvent(DemoEvent demoEvent) {
        String message = demoEvent.getMessage();
        System.out.println(message);
    }
}
