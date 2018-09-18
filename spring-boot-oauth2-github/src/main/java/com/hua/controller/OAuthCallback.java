/**
  * @filename OAuthCallback.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @type OAuthCallback
 * @description 
 * @author qianye.zheng
 */
@RestController
public class OAuthCallback extends BaseController
{
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping(value = "/callback", method = {RequestMethod.GET})
	public String callback()
	{
		System.out.println("OAuthCallback.callback()");
		
		return "hello world springboot oauth 2.x";
	}
	
}
