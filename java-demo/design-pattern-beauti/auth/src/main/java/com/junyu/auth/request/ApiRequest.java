package com.junyu.auth.request;

/**
 * @author haojunsheng
 * @date 2020/12/16 13:18
 */
public class ApiRequest {
    private String baseUrl;

    private String token;

    private String appId;

    private long timestamp;

    public ApiRequest(String baseUrl, String token, String appId, long timestamp) {
        this.baseUrl = baseUrl;
        this.token = token;
        this.appId = appId;
        this.timestamp = timestamp;
    }

    public static ApiRequest createFromFullUrl(String url) {
        return null;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public String getAppId() {
        return this.appId;
    }

    public String getToken() {
        return this.token;
    }

    public long getTimestamp() {
        return this.timestamp;
    }
}
