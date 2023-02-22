package com.hisoft.pam.im.security;

import com.hisoft.pam.im.auth.service.impl.MenuServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

import javax.servlet.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	@Autowired
	JwtUserUtil jwtUserUtil;
	@Autowired
	MenuServiceImpl menuServiceImpl;
	// 配置文件注入
	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	// 登陆后，每次访问资源都通过这个拦截器拦截
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(request, response, chain);
		List<String> menuList = new ArrayList<>();
		String requestUrl = fi.getRequestUrl();
		try {
			menuList = menuServiceImpl.getMenuList();
		} catch (Exception e) {
			invoke(fi);
		}

		if(!requestUrl.contains("/common/resource/checkCode/getImg")){
			for(String url : menuList){
				if(requestUrl.contains(url)||requestUrl.contains("/common/resource")||requestUrl.contains("/login")||requestUrl.contains("/administratives/getCity")||requestUrl.contains("/auth/companys/getAllCompany")
						||requestUrl.contains("/auth/departments/getList")||requestUrl.contains("/auth/companys/alltest")||requestUrl.contains("/auth/companys/alltestCopy")||requestUrl.contains("/auth/menus/userRoleList")||requestUrl.contains("/home")
						||requestUrl.contains("/guoran")||requestUrl.contains("/auth/users/bindWeiXinUrl")||requestUrl.contains("/auth/menus/getAllMenu")||requestUrl.contains("/auth/users/getCurrentUserInfo")
						||requestUrl.contains("/auth/users/page")||requestUrl.contains("/sales/ManagementSalesDetailsEntity/getSalesTable")||requestUrl.contains("/sales/InvoiceEntity/getByInvoiceMoney")||requestUrl.contains("/sys/syn")
						||requestUrl.contains("/auth/authDictItem/getItemsByDictCode")||requestUrl.contains("/sys/products")||requestUrl.contains("sys/wagePrice")||requestUrl.contains("/purchasein")
						||requestUrl.contains("/deviceInfo")||requestUrl.contains("/CostAccounting")||requestUrl.contains("/CostCalculation")||requestUrl.contains("/production/managementWarehousings")
						||requestUrl.contains("/miscellaneous")||requestUrl.contains("/employees/getAllEmployee")||requestUrl.contains("/deviceOverhaul")||requestUrl.contains("/deviceRepair")||requestUrl.contains("/deviceRepair")
						||requestUrl.contains("/deviceMaintain")||requestUrl.contains("/sales/CollectionEntitys")||requestUrl.contains("/customerInfos")||requestUrl.contains("/file")||requestUrl.contains("/guoran/loadimg")||requestUrl.contains("/JWT/sso/login")
						||requestUrl.contains("/v2/api-docs")){
					invoke(fi);
					break;
				}
			}
		}

//		if(!requestUrl.equals("/auth/menus/userRoleList")){
//			if(menuList.contains(requestUrl.split("/")[2])){
//				invoke(fi);
//			}else {
//				throw new RuntimeException("没有数据权限!");
//			}
//		}else {
//			invoke(fi);
//		}
	}

	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public Class<? extends Object> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	public void invoke(FilterInvocation fi) throws IOException, ServletException {
		// fi里面有一个被拦截的url
		// 里面调用MyInvocationSecurityMetadataSource的getAttributes(Object
		// object)这个方法获取fi对应的所有权限
		// 再调用MyAccessDecisionManager的decide方法来校验用户的权限是否足够
		InterceptorStatusToken token = super.beforeInvocation(fi);
		try {
			// 执行下一个拦截器
			fi.getChain().doFilter(fi.getRequest(), fi.getResponse());
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource newSource) {
		this.securityMetadataSource = newSource;
	}

	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
