package com.junyu.lesson41.second;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/4/11 下午2:10
 */
public class Logger {
    private FileWriter writer;
    private static final Logger instance = new Logger();

    private Logger() {
        File file = new File("/Users/wangzheng/log.txt");
        try {
            writer = new FileWriter(file, true); //true表示追加写入
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Logger getInstance() {
        return instance;
    }

    public void log(String message) throws IOException {
        writer.write(message);
    }
}
