package com.junyu.lesson44.five;

import com.junyu.lesson44.IRuleConfigParser;
import com.junyu.lesson44.PropertiesRuleConfigParser;
import com.junyu.lesson44.YamlRuleConfigParser;

/**
 * @author haojunsheng
 * @date 2021/4/13 23:41
 */
public class PropertiesConfigParserFactory implements IRuleConfigParserFactory {
    @Override
    public IRuleConfigParser createParser() {
        return new PropertiesRuleConfigParser();
    }
}
