package com.guoran.server.auth;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.guoran.server.auth.service.AuthService;
import com.guoran.server.auth.service.UserService;
import com.guoran.server.auth.vmodel.UserVM;
import com.guoran.server.common.JsonResult;
import com.guoran.server.common.exception.ImErrorCode;
import com.guoran.server.common.exception.ServiceException;
import com.guoran.server.common.i18n.MessageUtils;
import com.guoran.server.common.utils.AesEncryptUtil;
import com.guoran.server.security.JwtAuthenticationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@RestController
@Slf4j
public class LoginResource {

    @Autowired
    ObjectMapper objectMapper;
    @Autowired
    AuthService authService;
    @Autowired
    UserService userService;


    @RequestMapping(value = "/")
    String index() {
        return "index";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    String login(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpSession session) throws JsonProcessingException {
        String token = null;
        String checkCode = authenticationRequest.getCheckCode();
        String imgcode = (String) session.getAttribute("imgcode");
        if (!imgcode.equalsIgnoreCase(checkCode)) {
            session.removeAttribute("imgcode");
            return JsonResult.failed("验证码错误，请刷新验证码后重新登录！");
        }
        session.removeAttribute("imgcode");
        UserVM userVM = userService.getEntryByName(authenticationRequest.getUsername());
        if (userVM != null) {
            if (!userVM.isLocked()) {
                return JsonResult.failed(ImErrorCode.MSG_FAIL, "该用户已经注销！");
            }
        }
        JsonResult jsonResult = new JsonResult();
        String errorCode = "0";
        try {
            //解密密码
            String plaintext = AesEncryptUtil.aesDecrypt(authenticationRequest.getPassword());
            token = authService.login(authenticationRequest.getUsername(), plaintext);
            if (token.indexOf("不存在") != -1) {
                return JsonResult.failed(token);
            }
            log.info("{} {}：{}", authenticationRequest.getUsername(), MessageUtils.get("login.success"), LocalDateTime.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ServiceException se = new ServiceException("401", MessageUtils.get("login.fail"));
            throw se;
        }
        return JsonResult.success(errorCode, MessageUtils.get("login.success"), token);
    }

    @RequestMapping(value = "/loginGR", method = RequestMethod.POST)
    String loginGR(@RequestBody JwtAuthenticationRequest authenticationRequest, HttpSession session) throws JsonProcessingException {
        String token = null;
        UserVM userVM = userService.getEntryByName(authenticationRequest.getUsername());
        if (userVM != null) {
            if (!userVM.isLocked()) {
                return JsonResult.failed(ImErrorCode.MSG_FAIL, "该用户已经注销！");
            }
        }
        JsonResult jsonResult = new JsonResult();
        String errorCode = "0";
        try {
            //解密密码
            String plaintext = AesEncryptUtil.aesDecrypt(authenticationRequest.getPassword());
            token = authService.login(authenticationRequest.getUsername(), plaintext);
            if (token.indexOf("不存在") != -1) {
                return JsonResult.failed(token);
            }
            log.info("{} {}：{}", authenticationRequest.getUsername(), MessageUtils.get("login.success"), LocalDateTime.now());
        } catch (Exception e) {
            System.out.println(e.getMessage());
            ServiceException se = new ServiceException("401", MessageUtils.get("login.fail"));
            throw se;
        }
        return JsonResult.success(errorCode, MessageUtils.get("login.success"), token);
    }
}
