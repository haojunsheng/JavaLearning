//package com.ruc.utils_2;
//
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//
///**
// * @author 俊语
// * @date 2020/10/23 11:04
// */
//public class FutureDemo_23_1 {
//    public static void main(String[] args) {
//        ExecutorService executor = Executors.newFixedThreadPool(1);
//        // 创建 Result 对象 r
//        Result r = new Result();
//        r.setAAA(a);
//        // 提交任务
//        Future<Result> future = executor.submit(new Task(r), r);
//        Result fr = future.get();
//        // 下面等式成立
//        fr == = r;
//        fr.getAAA() == a;
//        fr.getXXX() == x;
//    }
//}
//
//class Task implements Runnable {
//    Result r;
//
//    // 通过构造函数传入 result
//    Task(Result r) {
//        this.r = r;
//    }
//
//    void run() {
//        // 可以操作 result
//        a = r.getAAA();
//        r.setXXX(x);
//    }
//}