package com.guoran.server.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * 用户
 */
public class JwtUser implements UserDetails {
    private final Long id;
    private final String username;
    private final String password;
    private final String realname;
    private final String email;
    private final List<? extends GrantedAuthority> authorities;
    private final Date lastPasswordResetDate;

    public JwtUser(
            Long id,
            String username,
            String password,
            String realname,
            String email,
            List<? extends GrantedAuthority> authorities,
            Date lastPasswordResetDate) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.realname = realname;
        this.email = email;
        this.authorities = authorities;
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    /**
     * 返回分配给用户的角色列表
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @JsonIgnore
    public Long getId() {
        return id;
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    /**
     * 姓名
     */
    @JsonIgnore
    public String getRealname() {
        return realname;
    }

    /**
     * 账户是否未过期
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * 账户是否未锁定
     */
    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * 密码是否未过期
     */
    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * 账户是否激活
     */
    @JsonIgnore
    @Override
    public boolean isEnabled() {
        return true;
    }

    /**
     * 这个是自定义的，返回上次密码重置日期
     */
    @JsonIgnore
    public Date getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

}
