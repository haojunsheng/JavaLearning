package com.junyu.lesson51.three.enrich;

import com.junyu.lesson51.three.ASensitiveWordsFilter;

/**
 * @author haojunsheng
 * @date 2021/4/17 19:21
 */
public class ASensitiveWordsFilterAdaptor implements ISensitiveWordsFilter {
    private ASensitiveWordsFilter aFilter;

    @Override
    public String filter(String text) {
        String maskedText = aFilter.filterSexyWords(text);
        maskedText = aFilter.filterPoliticalWords(maskedText);
        return maskedText;
    }
}
