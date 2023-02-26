package com.guoran.server.weChat;

import cn.hutool.core.util.RandomUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Component
public class SendWX {

    private static final String BASE_CHECK_CODES = "qwertyupkjhgfdsazxcvbnmQWERTYUPKJHGFDSAZXCVBNM23456789";


    //发送消息的执行方法
    public static String getToken(String url, String corpid, String corpsecret) {
        WeChatMsgSend swx = new WeChatMsgSend();
        String token = null;
        try {
            token = swx.getToken(url, corpid, corpsecret);
            System.out.println("获取到的token======>" + token);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return token;
    }


    //发送消息的执行方法
    public static String send(String userId) {
        WeChatMsgSend swx = new WeChatMsgSend();
        String code = RandomUtil.randomString(BASE_CHECK_CODES, 6);
        try {
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            HttpServletRequest request = attributes.getRequest();
            //这里的token获取待会会说从哪儿具体得到
            String token = swx.getToken("https://qyapi.weixin.qq.com/cgi-bin/gettoken", "ww02b35cb3d03e4a90", "UC2JA735Tr67GdJDMP02beS9psg6mpbkaAHoS-DhHVU");
            String postdata = swx.createpostdata(userId, "text", "1000002", "content", "果然风情系统修改密码使用的验证码：" + code);
            String resp = swx.post("utf-8", WeChatMsgSend.CONTENT_TYPE, (new WeChatUrlData()).getSendMessage_Url(), postdata, token);
            System.out.println("获取到的token======>" + token);
            System.out.println("请求数据======>" + postdata);
            System.out.println("发送微信的响应数据======>" + resp);
        } catch (Exception e) {
            e.getStackTrace();
        }
        return code;
    }
}
