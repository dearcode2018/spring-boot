package com.hua.interceptor;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @type ResourceInterceptor
 * @author qianye.zheng
 */
@Component
public class ResourceInterceptor implements HandlerInterceptor {

	/**
	 * 禁止访问的路径，路径黑名单
	 */
	@Value("${exclude.paths:/package.json}")
	List<String> forbidPaths;
	
	/**
	 * 
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//HandlerMethod
		return !forbidPaths.contains(request.getRequestURI());
	}
	
}
