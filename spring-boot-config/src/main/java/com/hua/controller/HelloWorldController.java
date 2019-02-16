/**
  * @filename HelloWorldController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hua.entity.User;

 /**
 * @type HelloWorldController
 * @description 
 * @author qianye.zheng
 */
/*
 * @RestController(value = 对象的名称，并非是接口路径) 
 */
/* 显式引入单独属性文件 */
@RestController
@RequestMapping("/hello")
public class HelloWorldController extends BaseController
{

	@Value("${my.secret}")
	private String mySecret;
	
	@Value("${my.number}")
	private Integer myNumber;
	
	@Value("${my.bignumber}")
	private Long myBignumber;
	
	@Value("${my.uuid}")
	private String myUUID;
	
	@Value("${my.number.less.than}")
	private Integer myNumberLessThan;
	
	@Value("${my.number.rang}")
	private Integer myNumberRange;
	
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
		System.out.println("values= " + mySecret);
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
	
}
