package com.guoran.server.config.swagger;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @description:
 * @author: machao
 * @create: 2019-12-10 11:37
 * @Modify By
 **/
@Component
@ConfigurationProperties(prefix = "file-service")
public class WebConfig {

    /**
     * 上传路径
     */
    private static String profile;

    public static String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        com.guoran.server.config.swagger.WebConfig.profile = profile;
    }

    // 获取上传路径
    public static String getUploadPath() {
        return profile + "upload/";
    }

    // 获取复制路径
    public static String getCopyPath() {
        return profile + "upload/copy/";
    }
}
