package com.hisoft.pam.im.common.utils;

public class AccessTokenlin {
    private String accessToken;
    private long expireTime;
    public AccessTokenlin(String accessToken, String expirein) {

        this.accessToken = accessToken;
        this.expireTime = System.currentTimeMillis() + Integer.parseInt(expirein)*1000;
    }
    //判断accessToken是否过期
    public boolean isExpired() {
        return System.currentTimeMillis()>expireTime;
    }
    public String getAccessToken() {
        return accessToken;
    }
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    public long getExpireTime() {
        return expireTime;
    }
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
}
