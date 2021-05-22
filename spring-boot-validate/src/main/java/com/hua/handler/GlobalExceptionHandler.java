package com.hua.handler;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.hua.bean.ResultBean;

/**
 * @type ExceptionHandler
 * @author qianye.zheng
 */
//@RestControllerAdvice
public class GlobalExceptionHandler {
	
	protected final Logger logger = LogManager.getLogger(this.getClass().getName());
	
	/* 是否展示参数异常信息，开发或测试环境开启 */
    @Value("${exception.hanlder.show.param-exception:false}")
    private boolean showParamException;
	
	
	/**
     * @param ex
     * @return
     * @description 参数异常 提示
     * @author qianye.zheng
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class, MissingServletRequestParameterException.class,
            BindException.class, ConstraintViolationException.class})
    protected ResultBean paramException(final HttpServletRequest request, final Exception ex) {
        if (showParamException) {
            logger.error("request uri: [ {} ] happen param exception: {}", request.getRequestURI(), ex.getMessage());
        }
        final ResultBean result = new ResultBean();
        if (ex instanceof MethodArgumentNotValidException) {
            final MethodArgumentNotValidException marn = (MethodArgumentNotValidException) ex;
            // 获取的注解填写的错误消息
            final FieldError fieldError = marn.getBindingResult().getFieldError();
            if (null != fieldError) {
                result.setMessage(fieldError.getDefaultMessage());
            } else {
                result.setMessage(marn.getLocalizedMessage());
            }
        } else if (ex instanceof BindException) {
            BindException be = (BindException) ex;
            FieldError fieldError = be.getBindingResult().getFieldError();
            if (null != fieldError) {
                result.setMessage(fieldError.getDefaultMessage());
            } else {
                result.setMessage(be.getLocalizedMessage());
            }
        } else if (ex instanceof ConstraintViolationException) {
            final ConstraintViolationException cvException = (ConstraintViolationException) ex;
            cvException.getConstraintViolations().stream().findFirst().ifPresent(x -> result.setMessage(x.getMessage()));
        } else {
            result.setMessage(ex.getMessage());
        }
        
        return result;
    }
    
    @ExceptionHandler(Exception.class)
    public ResultBean handleException(Exception e) {
    	final ResultBean result = new ResultBean();
    	result.setMessage("系统异常");
    	
    	return result;
    }
    
}
