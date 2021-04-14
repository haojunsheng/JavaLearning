package com.junyu.lesson45.first;

/**
 * @author haojunsheng
 * @date 2021/4/14 22:01
 */
public class RateLimiter {
    private RedisCounter redisCounter;

    public RateLimiter(RedisCounter redisCounter) {
        this.redisCounter = redisCounter;
    }

    public void test() {
        System.out.println("Hello World!");
    }
}
