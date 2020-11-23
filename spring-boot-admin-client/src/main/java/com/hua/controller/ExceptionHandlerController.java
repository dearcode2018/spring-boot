/**
  * @filename ExceptionHandlerController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hua.bean.PersonSearchBean;
import com.hua.bean.ResultBean;

/**
 * @type ExceptionHandlerController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/handler")
public class ExceptionHandlerController extends BaseController {

	private boolean exceptionSwitch = true;
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value={"/get/v1"})
	public ResultBean getV1(final String id) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + id + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param name
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value={"/get/v2"})
	public ResultBean getV2(final String id, final String name) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + name + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param name
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value={"/get/v3"})
	public ResultBean getV3(final HttpServletRequest request, 
			final HttpServletResponse response, final String id, final String name) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + name + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value={"/get/v4"})
	public ResultBean getV4() {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[当前用户]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param request
	 * @param response
	 * @param searchBean
	 * @return
	 * @author qianye.zheng
	 */
	@PostMapping(value={"/post/v1"})
	public ResultBean postV1(@RequestBody final PersonSearchBean searchBean) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + searchBean.getName() + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param request
	 * @param response
	 * @param searchBean
	 * @return
	 * @author qianye.zheng
	 */
	@PostMapping(value={"/post/v2"})
	public ResultBean postV2(final HttpServletRequest request, 
			final HttpServletResponse response, @RequestBody final PersonSearchBean searchBean) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + searchBean.getName() + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
	
	/**
	 * 
	 * @description 
	 * @param multipartFile
	 * @return
	 * @author qianye.zheng
	 */
	@PostMapping(value={"/upload/v1"})
	public ResultBean upload(@RequestParam(value = "file" ,required = false) final MultipartFile multipartFile) {
		/*
		 * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
		 */
		ResultBean result = new ResultBean();
		result.setMessage("收到[" + multipartFile.getOriginalFilename() + "]的请求");
		result.setMessageCode("205");
		result.setSuccess(true);
		if (exceptionSwitch) {
			throw new RuntimeException("发生运行时异常...");
		}
		
		return result;
	}
}
