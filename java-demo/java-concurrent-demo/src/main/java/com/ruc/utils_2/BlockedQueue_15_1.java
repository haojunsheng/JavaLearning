//package com.ruc.utils_2;
//
//import java.util.concurrent.locks.Condition;
//import java.util.concurrent.locks.Lock;
//import java.util.concurrent.locks.ReentrantLock;
//
///**
// * @author 俊语
// * @date 2020/10/22 17:44
// */
//public class BlockedQueue_15_1<T> {
//    final Lock lock = new ReentrantLock();
//    // 条件变量:队列不满
//    final Condition notFull = lock.newCondition();
//    // 条件变量:队列不空
//    final Condition notEmpty = lock.newCondition();
//
//    //入队
//    void enq(T x) {
//        lock.lock();
//        try {
//            while (队列已满) {
//                // 等待队列不满
//                notFull.await();
//            }
//            // 省略入队操作...
//            // 入队后, 通知可出队
//            notEmpty.signal();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//
//    //出队
//    void deq() {
//        lock.lock();
//        try {
//            while (队列已空) {
//                // 等待队列不空
//                notEmpty.await();
//            }
//            // 省略出队操作...
//            // 出队后，通知可入队
//            notFull.signal();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } finally {
//            lock.unlock();
//        }
//    }
//}