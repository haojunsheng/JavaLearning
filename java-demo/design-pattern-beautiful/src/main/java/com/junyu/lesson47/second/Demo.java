package com.junyu.lesson47.second;

import com.junyu.lesson47.SearchWord;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author haojunsheng
 * @date 2021/4/16 13:46
 */

public class Demo {
    private HashMap<String, SearchWord> currentKeywords = new HashMap<>();

    public void refresh() {
        HashMap<String, SearchWord> newKeywords = new LinkedHashMap<>();

        // 从数据库中取出所有的数据，放入到newKeywords中
        List<SearchWord> toBeUpdatedSearchWords = getSearchWords();
        for (SearchWord searchWord : toBeUpdatedSearchWords) {
            newKeywords.put(searchWord.getKeyword(), searchWord);
        }

        currentKeywords = newKeywords;
    }

    private List<SearchWord> getSearchWords() {
        // TODO: 从数据库中取出所有的数据
        return null;
    }
}