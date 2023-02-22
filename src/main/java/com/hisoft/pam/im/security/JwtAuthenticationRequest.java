package com.hisoft.pam.im.security;

import java.io.Serializable;

public class  JwtAuthenticationRequest implements Serializable {

    private static final long serialVersionUID = -8445943548965154778L;


    private String loginType;
    private String username;
    private String password;
    private String checkCode;



    public JwtAuthenticationRequest() {
        super();
    }

    public JwtAuthenticationRequest(String username, String password , String checkCode) {
        this.setUsername(username);
        this.setPassword(password);
        this.setCheckCode(checkCode);
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLoginType(){return this.loginType;}

    public void setLoginType(String loginType){this.loginType = loginType;}

    public String getCheckCode() {
        return checkCode;
    }

    public void setCheckCode(String checkCode) {
        this.checkCode = checkCode;
    }
}
