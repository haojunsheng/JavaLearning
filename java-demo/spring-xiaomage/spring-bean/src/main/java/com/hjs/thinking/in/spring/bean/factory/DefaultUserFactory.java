package com.hjs.thinking.in.spring.bean.factory;

import com.hjs.thinking.in.spring.ioc.overview.domain.User;

/**
 * @author 俊语
 * @date 2021/6/15 下午11:21
 */
public class DefaultUserFactory implements UserFactory {
    @Override
    public User createUser() {
        return User.createUser();
    }
}
