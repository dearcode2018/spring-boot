/**
  * @filename ThirdConfig.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.config;

import java.util.List;

import lombok.Data;

/**
 * @type ThirdConfig
 * @description 
 * @author qianye.zheng
 */
@Data
public class ThirdConfig
{
	
	/*
	 * like: 
	 * user.name
	 */
	private String name1;
	
	/* 格式 user.passwords[0] user.passwords[1]  */
	private List<String> passwords;
	
	private Integer age;
	
	private String address;
	
	private String remark;
	
}
