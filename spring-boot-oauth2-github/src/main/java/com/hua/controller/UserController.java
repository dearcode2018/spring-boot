/**
  * @filename UserController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.security.Principal;
import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

 /**
 * @type UserController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/")
public class UserController extends BaseController
{
	
	/**
	 * 
	 * @description 
	 * @param principal
	 * @return
	 * @author qianye.zheng
	 */
	@RequestMapping({ "/user", "/me" })
	public Map<String, String> user(Principal principal) {
		Map<String, String> map = new LinkedHashMap<>();
		map.put("name", principal.getName());
		return map;
	}	
	
	/**
	 * 
	 * @description 
	 * @param model
	 * @return
	 * @author qianye.zheng
	 */
	/*@GetMapping("/users")
	// 指定角色权限才能操作方法
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ModelAndView listUsers(Model model)
	{
		List<User> users = new ArrayList<User>();
		User user = null;
		user = new User();
		user.setId("20180915");
		user.setNickname("zhangsan");
		users.add(user);
		
		user = new User();
		user.setId("20180916");
		user.setNickname("lisi");
		users.add(user);
		model.addAttribute("title", "用户管理");
		model.addAttribute("users", users);
		
		return new ModelAndView("users/list", "userModel", model);
	}*/
	
}
