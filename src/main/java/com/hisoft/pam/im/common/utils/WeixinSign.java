package com.hisoft.pam.im.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Value;

/**
 * @description: 微信工具类
 * @author: machao
 * @create: 2020-07-08 13:53
 * @Modify By
 **/

public class WeixinSign {
    /**
     * 网页
     */
    @Value("${weixin.appid}")
    private   String wy_appid ;
    @Value("${weixin.secret}")
    private String wy_secret ;

    /**
     * 使用code获取AccessToken
     * @param code
     * @return
     */
    public JSONObject getAccessToken(String code){
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?";
        String params = "appid="+wy_appid+"&secret="+wy_secret+"&code="+code+"&grant_type=authorization_code";
        String result = HttpRequestUtil.httpGet(url + params);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    /**
     * 验证微信
     * @param access_token
     * @param openid
     * @return
     */
    public JSONObject getValidateData(String access_token,String openid){
        String url = "https://api.weixin.qq.com/sns/auth?access_token=" + access_token + "&openid=" + openid;
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    /**
     * 刷新token
     * @param refresh_token
     * @return
     */
    public JSONObject getRefreshToken(String refresh_token){
        String url = "https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=" + wy_appid + "&grant_type=refresh_token&refresh_token=" + refresh_token;
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

    /**
     * 获取用户信息
     * @param access_token
     * @param openid
     * @return
     */
    public JSONObject getUserInfo(String access_token,String openid){
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=" + access_token + "&openid=" + openid + "&lang=zh_CN";
        String result = HttpRequestUtil.httpGet(url);
        JSONObject data = JSON.parseObject(result);

        return data;
    }

}
