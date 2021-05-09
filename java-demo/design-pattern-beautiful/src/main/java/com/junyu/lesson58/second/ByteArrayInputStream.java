package com.junyu.lesson58.second;

import java.io.IOException;

/**
 * @author 俊语
 * @date 2021/5/9 下午6:10
 */
public class ByteArrayInputStream extends MyInputStream {
    //...省略其他代码...

    @Override
    public synchronized int read() {
//        return (pos < count) ? (buf[pos++] & 0xff) : -1;
        return -1;
    }

    @Override
    public void close() throws IOException {

    }
}
