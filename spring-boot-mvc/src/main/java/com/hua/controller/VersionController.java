/**
  * @filename VersionController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hua.entity.User;
import com.hua.util.ClassPathUtil;
import com.hua.util.FileUtil;
import com.hua.util.StringUtil;

 /**
 * @type VersionController
 * @description 
 * @author qianye.zheng
 */
/*
 * @RestController(value = 对象的名称，并非是接口路径) 
 */
@RestController
@RequestMapping("/vPrefix")
public class VersionController extends BaseController
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
	//@GetMapping(value = {"/get"}, headers = {"version=1"}, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	//@GetMapping(value = {"/get"}, headers = {"version=1"}, produces = MediaType.APPLICATION_JSON_VALUE)
	@GetMapping(value = {"/get"}, headers = {"version=1"})
	public User getV1(final String id, @CookieValue("myC") final String cookieValue)
	{
		System.out.println("getV1.values= " + id + ", cookieValue =" + cookieValue);
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("12345678");
		
		return user;
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value = {"/get"}, headers = {"version=2"})
	public User getV2(final String id)
	{
		System.out.println("getV2.values= " + id);
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		
		return user;
	}
	
	/**
	 * 
	 * @description 
	 * @param id
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping(value = {"/get"}, headers = {"version=3"})
	public User getV3(final String id)
	{
		System.out.println("getV3.values= " + id);
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		
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
