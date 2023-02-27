package com.guoran.server.security;

import com.guoran.server.auth.service.UserService;
import com.guoran.server.auth.vmodel.UserVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@SuppressWarnings("SpringJavaAutowiringInspection")
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    UserService userService;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtTokenUtil jwtTokenUtil;
    @Value("${jwt.header}")
    private String tokenHeader;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {
        String authHeader = request.getHeader(this.tokenHeader);
        if ("/login".equals(request.getRequestURI())) {
            chain.doFilter(request, response);
            return;
        }
        //Cookie[] cookies=request.getCookies();//会话型cookie适合手机
        if (authHeader == null) {
            authHeader = request.getParameter(this.tokenHeader);
        	/*if(authHeader==null &&cookies!=null){
        		for(Cookie cookie:cookies){
        			if(cookie.getName().equals("access_token"))
        			{
        				authHeader=tokenHead+" "+cookie.getValue();
        				break;
        			}
        		}
        	}*/
        }
        final String methodName = request.getMethod();
        if ("OPTIONS".equals(methodName)) {
            response.setStatus(HttpServletResponse.SC_OK);
            chain.doFilter(request, response);
        } else if (authHeader != null && authHeader.startsWith(tokenHead)) {
            final String authToken = authHeader.substring(tokenHead.length()); // The part after "Bearer "

            String username = jwtTokenUtil.getUsernameFromToken(authToken);

            logger.info("checking authentication " + username);

            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                // 如果我们足够相信token中的数据，也就是我们足够相信签名token的secret的机制足够好
                // 这种情况下，我们可以不用再查询数据库，而直接采用token中的数据
                // 本例中，我们还是通过Spring Security的 @UserDetailsService 进行了数据查询
                // 但简单验证的话，你可以采用直接验证token是否合法来避免昂贵的数据查询
                //查询用户有效性
                UserVM userVM = userService.getEntryByName(username);
                if (userVM != null) {
                    if (!userVM.isLocked()) {
                        throw new ServletException("Token失效");
                    }
                }
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

                if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(
                            request));
                    logger.info("authenticated user " + username + ", setting security context");
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                    
                    /*if(cookies==null||cookies.length==0){
		                Cookie cookie = new Cookie("access_token", authToken.trim());
		                cookie.setHttpOnly(true);
		                
		                response.addCookie(cookie);
                    }
                    else
                    {
                    	cookies[0].setValue(authToken.trim());
                    	cookies[0].setHttpOnly(true);
		                response.addCookie(cookies[0]);
                    }*/
                }
            }
        }

        chain.doFilter(request, response);
    }
}
