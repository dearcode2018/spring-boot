/**
  * @filename UserConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

 /**
 * @type UserConfig
 * @description 
 * @author qianye.zheng
 */
// 便于其他使用到的地方直接注入该对象
@Component
/* 使用 核心配置文件中的配置 application.properties， 
 * 高版本中已经去掉指定 自定义属性文件的 location字段 */
@ConfigurationProperties(prefix = "user", ignoreInvalidFields = true, ignoreUnknownFields = true)
public class UserConfig
{
	
	/*
	 * like: 
	 * user.name
	 */
	private String name;
	
	private String password;
	
	private Integer age;
	
	private String address;
	
	private String remark;

	/**
	 * @return the name
	 */
	public final String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public final void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the password
	 */
	public final String getPassword()
	{
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public final void setPassword(String password)
	{
		this.password = password;
	}

	/**
	 * @return the age
	 */
	public final Integer getAge()
	{
		return age;
	}

	/**
	 * @param age the age to set
	 */
	public final void setAge(Integer age)
	{
		this.age = age;
	}

	/**
	 * @return the address
	 */
	public final String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public final void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the remark
	 */
	public final String getRemark()
	{
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public final void setRemark(String remark)
	{
		this.remark = remark;
	}
	
}
