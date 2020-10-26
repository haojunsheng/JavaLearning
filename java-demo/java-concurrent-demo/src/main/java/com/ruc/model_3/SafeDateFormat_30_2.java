package com.ruc.model_3;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * @author 俊语
 * @date 2020/10/24 00:44
 */
public class SafeDateFormat_30_2 {
    public static void main(String[] args) throws InterruptedException {
        DateFormat df = SafeDateFormat.get();
        final DateFormat[] df1 = new DateFormat[1];
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                df1[0] = SafeDateFormat.get();

            }
        });
        thread.start();
        thread.join();
        System.out.println(df == df1[0]);
    }

    static class SafeDateFormat {
        // 定义 ThreadLocal 变量
        static final ThreadLocal<DateFormat> tl = ThreadLocal.withInitial(
                () -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));

        static DateFormat get() {
            return tl.get();
        }
    }
}
