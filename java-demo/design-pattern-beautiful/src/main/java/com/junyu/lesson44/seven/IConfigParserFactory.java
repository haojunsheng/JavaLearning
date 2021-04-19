package com.junyu.lesson44.seven;

import com.junyu.lesson44.IRuleConfigParser;

/**
 * @author haojunsheng
 * @date 2021/4/14 21:43
 */
public interface IConfigParserFactory {
    IRuleConfigParser createRuleParser();
    ISystemConfigParser createSystemParser();
}
