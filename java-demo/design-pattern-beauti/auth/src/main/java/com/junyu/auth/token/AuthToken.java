package com.junyu.auth.token;

import java.util.Map;

/**
 * 把 URL、AppID、密码、时间戳拼接为一个字符串；
 * 对字符串通过加密算法加密生成 token；
 *
 * @author haojunsheng
 * @date 2020/12/16 11:56
 */
public class AuthToken {
    private static final long DEFAULT_TIME_INTERVAL = 1 * 60 * 1000;

    private String token;

    private long createTime;

    private long expiredTimeInterval = DEFAULT_TIME_INTERVAL;

    public AuthToken(String token, long createTime) {
        this.token = token;
        this.createTime = createTime;
    }

    public AuthToken(String token, long createTime, long expiredTimeInterval) {
        this.token = token;
        this.createTime = createTime;
        this.expiredTimeInterval = expiredTimeInterval;
    }

    public static AuthToken create(String baseUrl, long createTime, Map<String, String> params) {
        return null;
    }

    public String getToken() {
        return token;
    }

    /**
     * 根据时间戳判断 token 是否过期失效
     *
     * @return
     */
    public boolean isExpired() {
        return createTime + expiredTimeInterval < System.currentTimeMillis();
    }

    /**
     * 验证两个 token 是否匹配
     *
     * @param authToken
     * @return
     */
    public boolean match(AuthToken authToken) {
        return token.equals(authToken.getToken());
    }

    public static void main(String[] args) {

    }
}
