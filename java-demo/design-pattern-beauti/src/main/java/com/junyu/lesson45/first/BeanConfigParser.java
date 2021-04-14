package com.junyu.lesson45.first;

import java.io.InputStream;
import java.util.List;

/**
 * @author 俊语
 * @date 2021/4/15 上午12:09
 */
public interface BeanConfigParser {
    List parse(InputStream inputStream);
    List parse(String configContent);
}
