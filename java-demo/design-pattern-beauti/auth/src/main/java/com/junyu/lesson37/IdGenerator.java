package com.junyu.lesson37;

/**
 * @author haojunsheng
 * @date 2021/4/8 19:41
 */
public interface IdGenerator {
    String generate() throws IdGenerationFailureException;
}
