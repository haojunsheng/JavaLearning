package com.junyu.lesson44.five;

import com.junyu.lesson44.IRuleConfigParser;

/**
 * @author haojunsheng
 * @date 2021/4/13 23:40
 */
public interface IRuleConfigParserFactory {
    IRuleConfigParser createParser();
}
