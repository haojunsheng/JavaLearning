package com.junyu.lesson48.four;

import com.junyu.lesson48.IUserController;
import com.junyu.lesson48.first.UserController;

/**
 * @author haojunsheng
 * @date 2021/4/17 15:40
 */
public class Demo {
    public static void main(String[] args) {
        MetricsCollectorProxy proxy = new MetricsCollectorProxy();
        IUserController userController = (IUserController) proxy.createProxy(new UserController());
        System.out.println(userController);
    }
}
