package com.hjs.thinking.in.spring.bean.factory;

import com.hjs.thinking.in.spring.ioc.overview.domain.User;

/**
 * @author 俊语
 * @date 2021/6/15 下午11:20
 */
public interface UserFactory {
    default User createUser() {
        return User.createUser();
    }
}
