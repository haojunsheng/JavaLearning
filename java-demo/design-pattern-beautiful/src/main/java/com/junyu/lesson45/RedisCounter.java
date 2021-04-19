package com.junyu.lesson45;

/**
 * @author haojunsheng
 * @date 2021/4/14 22:02
 */
public class RedisCounter {
    private String ipAddress;
    private int port;

    public RedisCounter(String ipAddress, Integer port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    public int increamentAndGet() {
        System.out.println("Connect to " + this.ipAddress + ":" + this.port);
        return 10;
    }
}
