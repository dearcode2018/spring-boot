/**
  * @filename LoginController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hua.bean.ResultBean;
import com.hua.entity.User;

/**
 * @type LoginController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping(value = "/api/user")
public class LoginController
{
 
    @Autowired
    private StringRedisTemplate firstRedisTemplate;
 
    /**
     * 
     * @description 
     * @param request
     * @param account
     * @param password
     * @return
     * @author qianye.zheng
     */
    @GetMapping("/login")
    public ResultBean login(HttpServletRequest request, String account, String password)
    {
    	Integer userId = 123;
        HttpSession session = request.getSession();
        session.setAttribute("loginUserId", userId);
        //firstRedisTemplate.opsForValue().set("loginUser:" + userId, session.getId());

        return new ResultBean(true, "登录成功！");
    }
 
    /**
     * 
     * @description 
     * @param userId
     * @return
     * @author qianye.zheng
     */
    @GetMapping(value = "/getUserInfo")
    public ResultBean get(long userId)
    {
        User user = new User();
        user.setId("123");
        user.setUsername("guangzhou");
        ResultBean resultBean = new ResultBean();
        resultBean.setData(user);
        
        return resultBean;
    }
}
