/**
  * @filename CustomErrorWebExceptionHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import org.springframework.boot.autoconfigure.web.ResourceProperties;
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler;
import org.springframework.boot.web.reactive.error.ErrorAttributes;
import org.springframework.context.ApplicationContext;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

/**
 * @type CustomErrorWebExceptionHandler
 * @description 
 * @author qianye.zheng
 */
public class CustomErrorWebExceptionHandler
		extends AbstractErrorWebExceptionHandler
{

	/*
	 * 继承 AbstractErrorWebExceptionHandler
	 * 或 DefaultErrorWebExceptionHandler
	 * 
	 * 
	 */
	
	/**
	 * @description 构造方法
	 * @param errorAttributes
	 * @param resourceProperties
	 * @param applicationContext
	 * @author qianye.zheng
	 */
	public CustomErrorWebExceptionHandler(ErrorAttributes errorAttributes,
			ResourceProperties resourceProperties,
			ApplicationContext applicationContext)
	{
		super(errorAttributes, resourceProperties, applicationContext);
	}

	/**
	 * @description 
	 * @param errorAttributes
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	protected RouterFunction<ServerResponse> getRoutingFunction(
			ErrorAttributes errorAttributes)
	{
		RouterFunctions.route(null, null);
		 
		return null;
	}

}
