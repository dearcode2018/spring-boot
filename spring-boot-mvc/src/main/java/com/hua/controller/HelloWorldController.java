/**
  * @filename HelloWorldController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hua.entity.User;
import com.hua.util.ClassPathUtil;
import com.hua.util.FileUtil;
import com.hua.util.StringUtil;

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
	
	//@Value("${system.name}")
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
		System.out.println("values= " + value);
		System.out.println(this.getClass().getResource("/").getPath());
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("12345678");
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
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		//System.out.println(JacksonUtil.writeAsString(user));
		
		return user;
	}
	
	/**
	 * 
	 * @description 模拟内存溢出，观察系统运行状况
	 * @param amount
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value = "/outOfMemoryError")
	public User outOfMemoryError(final Integer amount)
	{
		User user = new User();
		user.setNickname("张三");
		user.setPassword("123456");
		String content = FileUtil.getString(ClassPathUtil.getClassPath("/info_01.txt"));
		StringBuilder builder = StringUtil.getBuilder();
		// 循环遍历，直至内存溢出
		for (int i = 0; i < amount; i++)
		{
			builder.append(content);
		}
		
		return user;
	}
	
}
