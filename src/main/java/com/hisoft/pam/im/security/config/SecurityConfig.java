package com.hisoft.pam.im.security.config;

import com.hisoft.pam.im.security.*;
import com.hisoft.pam.im.security.*;
import com.hisoft.pam.im.security.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ComponentScan
@EnableAutoConfiguration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private JwtAuthenticationEntryPoint unauthorizedHandler;
	// Spring会自动寻找同样类型的具体类注入，这里就是JwtUserDetailsServiceImpl了
	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	public void configureAuthentication(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
		authenticationManagerBuilder
				// 设置UserDetailsService
				.userDetailsService(this.userDetailsService)
				// 使用BCrypt进行密码的hash
				.passwordEncoder(passwordEncoder());
	}

	// 装载BCrypt密码编码器
	@Bean
	public PasswordEncoder passwordEncoder() {
		//return new Md5PasswordEncoder(); 
		return new BCryptPasswordEncoder(10);
	}

	@Bean
	public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
		return new JwtAuthenticationTokenFilter();
	}
	@Bean
	public MyInvocationSecurityMetadataSource securityMetadataSource(){
		return new MyInvocationSecurityMetadataSource();
	}
	@Bean
	public MyAccessDecisionManager accessDecisionManager(){
		return new MyAccessDecisionManager();
	}
	@Bean
	public MyFilterSecurityInterceptor myFilter() throws Exception{
		MyFilterSecurityInterceptor filter =new MyFilterSecurityInterceptor();
		filter.setAccessDecisionManager(accessDecisionManager());
		filter.setSecurityMetadataSource(securityMetadataSource());
		filter.setAuthenticationManager(authenticationManager());
		return filter;
	}
	// @formatter:off
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			//表示页面可以在相同域名页面的 frame 中展示。
			.headers().frameOptions().sameOrigin().and()
			// 由于使用的是JWT，我们这里不需要csrf
			.csrf().disable()
			// 无权限时的处理
			.exceptionHandling().authenticationEntryPoint(unauthorizedHandler).and()
			// 基于token，所以不需要session
			.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
			.authorizeRequests()
			.antMatchers(HttpMethod.GET, "/"	).permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-ui.html/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger/**").permitAll()
			.antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger-resources/**").permitAll()
			.antMatchers(HttpMethod.GET, "/swagger/**").permitAll()
			.antMatchers(HttpMethod.GET, "/v2/api-docs").permitAll()
			.antMatchers(HttpMethod.GET, "/v2/**").permitAll()
			.antMatchers(HttpMethod.GET, "/static/**").permitAll()
			.antMatchers(HttpMethod.GET, "/configuration/**").permitAll()
			.antMatchers(HttpMethod.GET, "/images/**").permitAll()
			.antMatchers(HttpMethod.GET, "/actuator/**").permitAll()
			.antMatchers(HttpMethod.POST,"/login").permitAll()
			.antMatchers(HttpMethod.GET,"/JWT/sso/login").permitAll()
			.antMatchers(HttpMethod.POST,"/loginGR").permitAll()
		     .antMatchers(HttpMethod.GET,"/common/resource/checkCode/getImg").permitAll()
			.antMatchers(HttpMethod.GET,"/weChat/accessTokens/send/**").permitAll()
			.antMatchers(HttpMethod.POST,"/auth/users/resetPassword").permitAll()
			.antMatchers(HttpMethod.GET,"/s/**").permitAll()
			.antMatchers(HttpMethod.GET,"/pam/loadimg/**").permitAll()
			.anyRequest().authenticated();
		// 添加JWT filter
		http.addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
		http.addFilterBefore(myFilter(), FilterSecurityInterceptor.class);
		// 禁用缓存
		http.headers().cacheControl();
	}
	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
}
