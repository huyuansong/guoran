package com.guoran.server.auth.service;

/**
 * token颁发和刷新
 */
public interface AuthService {
    /**
     * 根据用户名和密码获取token
     * @param username
     * @param password
     * @return
     */
    String login(String username, String password);

    /**
     * 根据旧的token获取新的token
     * @param oldToken
     * @return
     */
    String refresh(String oldToken);
}
