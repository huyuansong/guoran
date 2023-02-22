package com.hisoft.pam.im.security;

import com.hisoft.pam.im.auth.vmodel.UserVM;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 */
public final class JwtUserFactory {

    private JwtUserFactory() {
    }
	public static JwtUser create(UserVM user) {
    	List roles=new ArrayList<String>();
    	roles.add("ROLE_USER");
        return new JwtUser(
                user.getId(),
                user.getName(),
                user.getPassword(),
                user.getRealName(),
                user.getEmail(),
                mapToGrantedAuthorities(roles),
                new Date()
        );
    }

    private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
        return authorities.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }
}
