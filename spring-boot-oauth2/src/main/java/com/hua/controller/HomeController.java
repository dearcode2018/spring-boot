/**
  * @filename HomeController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @type HomeController
 * @description 
 * @author qianye.zheng
 */
@RestController
public class HomeController extends BaseController
{
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping("/home")
	public String home()
	{
		return "hello world springboot oauth 2.x";
	}
	
}
