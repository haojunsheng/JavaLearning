package com.junyu.lesson37;

import java.net.UnknownHostException;

/**
 * @author haojunsheng
 * @date 2021/4/9 21:09
 */
public class IdGenerationFailureException extends Exception {

    public IdGenerationFailureException(String s) {
    }

    public IdGenerationFailureException(String s, UnknownHostException e) {
    }
}
