/**
  * @filename MainController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

 /**
 * @type MainController
 * @description 
 * @author qianye.zheng
 */
@Controller
public class MainController extends BaseController
{
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping("/")
	public String root()
	{
		return "redirect:/index";
	}
	
	/**
	 * 
	 * @description 
	 * @param principal
	 * @param model
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping("/index")
	public String index(final Principal principal, final Model model)
	{
		if (null == principal)
		{
			return "index";
		}
		System.out.println(principal.toString());
		model.addAttribute("principal", principal);
		
		return "index";
	}
	
	/**
	 * 
	 * @description 
	 * @return
	 * @author qianye.zheng
	 */
	@GetMapping("/403")
	public String accessDenied()
	{
		return "403";
	}
	
}
