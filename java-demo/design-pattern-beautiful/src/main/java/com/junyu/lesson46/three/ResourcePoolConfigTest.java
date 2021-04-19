package com.junyu.lesson46.three;

/**
 * @author haojunsheng
 * @date 2021/4/15 22:44
 */
public class ResourcePoolConfigTest {
    public static void main(String[] args) {
        ResourcePoolConfig config = new ResourcePoolConfig.Builder()
                .setName("dbconnectionpool").setMaxTotal(16).setMaxIdle(10).setMinIdle(12).build();
    }
}
