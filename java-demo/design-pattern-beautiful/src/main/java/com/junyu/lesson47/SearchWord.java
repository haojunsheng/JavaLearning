package com.junyu.lesson47;

/**
 * @author haojunsheng
 * @date 2021/4/16 13:40
 */
public class SearchWord {
    private long lastUpdateTime;
    private String keyword;
    private int count;

    public SearchWord(String keyword, int count, long lastUpdateTime) {
        this.count = count;
        this.keyword = keyword;
        this.lastUpdateTime = lastUpdateTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }
}
