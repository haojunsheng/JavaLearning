package com.junyu.lesson41.first;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/4/10 下午11:45
 */
public class Logger {
    private FileWriter writer;

    public Logger() throws IOException {
        File file = new File("/Users/wangzheng/log.txt");
        writer = new FileWriter(file, true); //true表示追加写入
    }

    public void log(String message) throws IOException {
        writer.write(message);
    }
}
