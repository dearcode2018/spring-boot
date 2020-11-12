/**
  * @filename HelloWorldController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import com.hua.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;


/**
 * @type HelloWorldController
 * @description 
 * @author qianye.zheng
 */
/*
 * @RestController(value = 对象的名称，并非是接口路径) 
 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController extends BaseController
{
	
	@Value("${system.name:123}")
	private String value;
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping({"/get", "/get23"})
	public User get(final String id)
	{
		String str = null;
		Integer c = null;
		int b = a;
		System.out.println("values= " + value);
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("12345678");
		//System.out.println(JacksonUtil.writeAsString(user));
		if (1 == a) {

		}

		return user;
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping(value = "/get2", method = RequestMethod.GET)
	public User get2(final String id)
	{
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		//System.out.println(JacksonUtil.writeAsString(user));
		
		return user;
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping(value = "/get3", method = RequestMethod.GET)
	public User get3(final String id)
	{
		Map map = null;
		String s = null;
		System.out.println(2);
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		//System.out.println(JacksonUtil.writeAsString(user));
		
		return user;
	}
	
}
