package com.junyu.lesson44.six;

import com.junyu.lesson44.IRuleConfigParser;
import com.junyu.lesson44.InvalidRuleConfigException;
import com.junyu.lesson44.RuleConfig;
import com.junyu.lesson44.five.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author haojunsheng
 * @date 2021/4/14 21:30
 */
public class RuleConfigSource {
    public RuleConfig load(String ruleConfigFilePath) throws InvalidRuleConfigException {
        String ruleConfigFileExtension = getFileExtension(ruleConfigFilePath);

        IRuleConfigParserFactory parserFactory = RuleConfigParserFactoryMap.getParserFactory(ruleConfigFileExtension);
        if (parserFactory == null) {
            throw new InvalidRuleConfigException("Rule config file format is not supported: " + ruleConfigFilePath);
        }
        IRuleConfigParser parser = parserFactory.createParser();

        String configText = "";
        //从ruleConfigFilePath文件中读取配置文本到configText中
        RuleConfig ruleConfig = parser.parse(configText);
        return ruleConfig;
    }

    private String getFileExtension(String filePath) {
        //...解析文件名获取扩展名，比如rule.json，返回json
        return "json";
    }
}


