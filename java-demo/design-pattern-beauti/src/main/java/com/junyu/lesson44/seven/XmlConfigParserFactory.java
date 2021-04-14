package com.junyu.lesson44.seven;

import com.junyu.lesson44.IRuleConfigParser;
import com.junyu.lesson44.XmlRuleConfigParser;

/**
 * @author haojunsheng
 * @date 2021/4/14 21:45
 */
public class XmlConfigParserFactory implements IConfigParserFactory{
    @Override
    public IRuleConfigParser createRuleParser() {
        return new XmlRuleConfigParser();
    }

    @Override
    public ISystemConfigParser createSystemParser() {
        return new XmlSystemConfigParser();
    }
}
