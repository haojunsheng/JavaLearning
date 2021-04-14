package com.junyu.lesson45.first;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 俊语
 * @date 2021/4/15 上午12:09
 */
public class XmlBeanConfigParser implements BeanConfigParser {
    @Override
    public List parse(InputStream inputStream) {
        String content = null;
        // TODO:...
        return parse(content);
    }

    @Override
    public List parse(String configContent) {
        List beanDefinitions = new ArrayList<>();
        // TODO:...
        return beanDefinitions;
    }
}
