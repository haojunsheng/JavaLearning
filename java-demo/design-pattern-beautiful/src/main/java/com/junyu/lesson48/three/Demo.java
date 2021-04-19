package com.junyu.lesson48.three;

import com.junyu.lesson48.second.UserController;

/**
 * @author haojunsheng
 * @date 2021/4/17 15:08
 */
public class Demo {
    public static void main(String[] args) {
        UserController userController = new UserControllerProxy();
        System.out.println(userController);
    }
}
