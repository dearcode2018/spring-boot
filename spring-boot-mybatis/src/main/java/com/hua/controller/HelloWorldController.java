/**
  * @filename HelloWorldController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hua.dao.PersonDao;
import com.hua.entity.Person;
import com.hua.entity.User;
import com.hua.util.JacksonUtil;

 /**
 * @type HelloWorldController
 * @description 
 * @author qianye.zheng
 */
/*
 * @RestController(value = 对象的名称，并非是接口路径) 
 */
@RestController
public class HelloWorldController extends BaseController
{
	
	//@Resource
	//private PersonDao personDao;
	
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
		User user = new User();
		user.setNickname("张三");
		user.setId(id);
		user.setPassword("123456");
		//System.out.println(JacksonUtil.writeAsString(user));
		//Person p = personDao.get("3");
		//System.out.println(JacksonUtil.writeAsString(p));
		
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
