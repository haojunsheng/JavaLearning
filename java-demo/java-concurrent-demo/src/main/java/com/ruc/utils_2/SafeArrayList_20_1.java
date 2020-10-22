package com.ruc.utils_2;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊语
 * @date 2020/10/22 20:21
 */
public class SafeArrayList_20_1<T> {
    // 封装 ArrayList
    List<T> c = new ArrayList<>();

    // 控制访问路径
    synchronized T get(int idx) {
        return c.get(idx);
    }

    synchronized void add(int idx, T t) {
        c.add(idx, t);
    }

    synchronized boolean addIfNotExist(T t) {
        if (!c.contains(t)) {
            c.add(t);
            return true;
        }
        return false;
    }
}
