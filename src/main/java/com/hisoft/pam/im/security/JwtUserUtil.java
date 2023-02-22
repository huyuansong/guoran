package com.hisoft.pam.im.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @description: 登陆用户信息
 * @author: machao
 * @create: 2019-12-09 14:49
 * @Modify By
 **/
@Component
public class JwtUserUtil {
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.tokenHead}")
    private String tokenHead;
    @Value("${jwt.header}")
    private String tokenHeader;
    /**
     * 用户名
     * @return
     */
    public String getUserName(){
        String authToken = getToken();
        String userName=jwtTokenUtil.getUsernameFromToken(authToken);
        return userName;
    }

    /**
     * 真实姓名
     * @return
     */
    public String getRealName() {
        String authToken = getToken();
        String realName=jwtTokenUtil.getRealnameFromToken(authToken);
        return realName;
    }

    private String getToken(){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();
        Map map = WebUtils.getParametersStartingWith(request,null);
        String token =request.getHeader(this.tokenHeader);
        if(token==null){
            token = request.getParameter(this.tokenHeader).toString();
        }
        String authToken = token.substring(tokenHead.length());
        return authToken;
    }
}
