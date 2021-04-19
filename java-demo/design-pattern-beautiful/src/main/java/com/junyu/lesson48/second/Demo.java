package com.junyu.lesson48.second;

import com.junyu.lesson48.IUserController;

/**
 * @author haojunsheng
 * @date 2021/4/16 20:08
 */
public class Demo {
    public static void main(String[] args) {
        IUserController userController = new UserControllerProxy(new UserController());
        userController.login("1", "2");
    }
}
