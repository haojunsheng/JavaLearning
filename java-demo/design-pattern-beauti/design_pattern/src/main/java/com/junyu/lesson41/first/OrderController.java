package com.junyu.lesson41.first;

import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/4/10 下午11:47
 */
public class OrderController {
    private Logger logger = new Logger();

    public OrderController() throws IOException {
    }

    public void create(OrderVo order) throws IOException {
        // ...省略业务逻辑代码...
        logger.log("Created an order: " + order.toString());
    }
}
