/**
 * 描述: 
 * BaseController.java
 * 
 * @author qye.zheng
 *  version 1.0
 */
package com.hua.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.hua.bean.ResultBean;

/**
 * 描述: 
 * 
 * @author qye.zheng
 * BaseController
 */
//@Controller
public abstract class BaseController {
	
	/* apache commons log */
	protected Log log = LogFactory.getLog(this.getClass().getName());
	
	/**
	 * 
	 * @description 
	 * @param ex
	 * @return
	 * @author qianye.zheng
	 */
	@ExceptionHandler(value = MethodArgumentNotValidException.class)
	protected ResultBean handleBindException(final MethodArgumentNotValidException ex)
	{
		final ResultBean resultBean = new ResultBean();
		// 获取的注解填写的错误消息
		resultBean.setMessage(ex.getBindingResult().getFieldError().getDefaultMessage());
		resultBean.setSuccess(false);
		
		return resultBean;
	}
	
	
	/**
	 * 
	 * @description 
	 * @param ex
	 * @return
	 * @author qianye.zheng
	 */
	@ExceptionHandler(value = Exception.class)
	protected ResultBean handleBindException(final Exception ex)
	{
		final ResultBean resultBean = new ResultBean();
		// 获取的注解填写的错误消息
		resultBean.setMessage("系统异常");
		resultBean.setSuccess(false);
		
		return resultBean;
	}
}
