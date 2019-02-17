/**
  * @filename MyErrorViewResolver.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.resolver;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.ModelAndView;

/**
 * @type MyErrorViewResolver
 * @description 
 * @author qianye.zheng
 */
public class MyErrorViewResolver implements ErrorViewResolver
{

	/**
	 * @description 
	 * @param request
	 * @param status
	 * @param model
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public ModelAndView resolveErrorView(HttpServletRequest request,
			HttpStatus status, Map<String, Object> model)
	{
		System.out.println("MyErrorViewResolver.resolveErrorView()");
		
		return null;
	}

}
