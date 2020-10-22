//package com.ruc.utils_2;
//
///**
// * @author 俊语
// * @date 2020/10/23 00:43
// */
//// 采用一般意义上池化资源的设计方法
//public class ThreadPool_22_1 {
//    // 获取空闲线程
//    void acquire() {
//    }
//
//    // 释放线程
//    void release(Thread t) {
//    }
//
//    public static void main(String[] args) {
//        // 期望的使用
//        ThreadPool_22_1 pool;
//        Thread T1 = pool.acquire();
//        // 传入 Runnable 对象
//        T1.execute(() -> {
//            // 具体业务逻辑
//        });
//    }
//}
