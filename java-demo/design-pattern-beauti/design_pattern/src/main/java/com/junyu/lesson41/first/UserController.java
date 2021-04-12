package com.junyu.lesson41.first;

import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/4/10 下午11:47
 */
public class UserController {
    private Logger logger = new Logger();

    public UserController() throws IOException {
    }

    public void login(String username, String password) throws IOException {
        // ...省略业务逻辑代码...
        logger.log(username + " logined!");
    }
}
