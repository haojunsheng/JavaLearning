//package com.ruc.utils_2;
//
//import java.util.concurrent.Executor;
//import java.util.concurrent.Executors;
//
///**
// * @author 俊语
// * @date 2020/10/22 19:59
// */
//public class CountDownLatch {
//    public static void main(String[] args) {
//        Executor executor = Executors.newFixedThreadPool(2);
//        while (存在未对账订单) {
//            // 计数器初始化为2
//            CountDownLatch latch = new CountDownLatch(2);
//            // 查询未对账订单
//            executor.execute(() -> {
//                pos = getPOrders();
//                latch.countDown();
//            });
//            // 查询派送单
//            executor.execute(() -> {
//                dos = getDOrders();
//                latch.countDown();
//            });
//            // 等待两个查询操作结束
//            latch.await();
//            // 执行对账操作
//            diff = check(pos, dos);
//            // 差异写入差异库
//            save(diff);
//        }
//    }
//}
