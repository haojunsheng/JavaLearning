package com.ruc.utils_2;

import java.util.List;
import java.util.Vector;
import java.util.function.Function;

/**
 * @author 俊语
 * @date 2020/10/22 19:14
 */
public class ObjPool_16_2<T, R> {
    final List<T> pool;
    // 用信号量实现限流器
    final Semaphore_16_1 sem;

    // 构造函数
    ObjPool_16_2(int size, T t) {
        pool = new Vector<T>() {
        };
        for (int i = 0; i < size; i++) {
            pool.add(t);
        }
        sem = new Semaphore_16_1(size);
    }

    // 利用对象池的对象，调用 func
    R exec(Function<T, R> func) {
        T t = null;
        sem.acquire();
        try {
            t = pool.remove(0);
            return func.apply(t);
        } finally {
            pool.add(t);
            sem.release();
        }
    }

    public static void main(String[] args) {
        // 创建对象池
        ObjPool_16_2<Integer, String> pool =
                new ObjPool_16_2<Integer, String>(10, 2);
        // 通过对象池获取 t，之后执行
        pool.exec(t -> {
            System.out.println(t);
            return t.toString();
        });
    }
}


