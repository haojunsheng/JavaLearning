package com.junyu.lesson44.seven;

import com.junyu.lesson44.IRuleConfigParser;
import com.junyu.lesson44.JsonRuleConfigParser;

/**
 * @author haojunsheng
 * @date 2021/4/14 21:44
 */
public class JsonConfigParserFactory implements IConfigParserFactory {
    @Override
    public IRuleConfigParser createRuleParser() {
        return new JsonRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser() {
        return new JsonSystemConfigParser();
    }
}
