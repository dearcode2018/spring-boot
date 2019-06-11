
/**
  * @filename ConfigController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hua.actuator.FooConfig;
import com.hua.bean.ResultBean;

/**
 * @type ConfigController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/config")
public class ConfigController extends BaseController
{
	
	@Resource
	private FooConfig fooConfig;
	
	/**
	 * 支持改方法声明发布到远程
	 */
	
	/**
	 * 
	 * @description 获取值
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping({"/getValue/{id}"})
	public ResultBean getValue(final @PathVariable("id") String id)
	{
		System.out.println("ConfigController.getValue()");
		ResultBean result = new ResultBean();
		result.setMessage("值["  + fooConfig.getValue() + "]" + id);
		result.setMessageCode("205");
		result.setSuccess(true);
		
		return result;
	}
	
}
