/**
  * @filename GlobalExceptionHandler.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.handler;

import javax.servlet.http.HttpServletRequest;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hua.bean.ResultBean;

/**
 * @type GlobalExceptionHandler
 * @description 
 * @author qianye.zheng
 */
@RestControllerAdvice
@ControllerAdvice
//@ResponseBody
public class GlobalExceptionHandler {

	protected final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
    @ExceptionHandler(Exception.class)
	public ResultBean exception(HttpServletRequest request, Exception exp) {
		logger.error("local-请求异常, URL:{} ", request.getRequestURI(), exp);
		final ResultBean resultBean = new ResultBean();
		resultBean.setSuccess(false);
		resultBean.setMessage("系统异常");
		
		return resultBean;
	}
	
}
