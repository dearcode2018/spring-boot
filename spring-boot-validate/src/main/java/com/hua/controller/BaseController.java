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
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
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
	 * @description 参数异常 提示
	 * @param ex
	 * @return
	 * @author qianye.zheng
	 */
	@ExceptionHandler(value = {MethodArgumentNotValidException.class, MissingServletRequestParameterException.class})
	protected ResultBean paramException(final Exception ex)
	{
	    ex.printStackTrace();
		final ResultBean resultBean = new ResultBean();
		if (ex instanceof MethodArgumentNotValidException) {
		    final MethodArgumentNotValidException marn = (MethodArgumentNotValidException) ex;
		    // 获取的注解填写的错误消息
		    final FieldError fieldError = marn.getBindingResult().getFieldError();
		    if (null != fieldError) {
		        resultBean.setMessage(fieldError.getDefaultMessage());
		    } else {
		        resultBean.setMessage(marn.getLocalizedMessage());
		    }
		} else {
		    resultBean.setMessage(ex.getMessage());
		}
		resultBean.setSuccess(false);
		
		return resultBean;
	}
	
	/**
	 * 
	 * @description 其他异常
	 * @param ex
	 * @return
	 * @author qianye.zheng
	 */
	@ExceptionHandler(value = Exception.class)
	protected ResultBean otherException(final Exception ex)
	{
	    ex.printStackTrace();
		final ResultBean resultBean = new ResultBean();
		// 获取的注解填写的错误消息
		resultBean.setMessage("系统异常");
		//resultBean.setMessage(ex.getMessage());
		resultBean.setSuccess(false);
		
		return resultBean;
	}
}
