/**
  * @filename LoginController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hua.bean.ResultBean;
import com.hua.constant.CacheKeys;
import com.hua.entity.User;

/**
 * @type LoginController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping(value = "/user")
public class LoginController
{
 
    /**
     * 名称可以为: redisService/redisService
     * 不要命名为redisTemplate，因为这是另外一个Bean的名称对应RedisTemplate
     */
    @Resource
    private StringRedisTemplate redisService;
 
    /**
     * 
     * @description 
     * @param request
     * @param account
     * @param password
     * @return
     * @author qianye.zheng
     */
    @PostMapping("/login")
    public ResultBean login(HttpServletRequest request, String account, String password)
    {
    	Long userId = 123L;
        HttpSession session = request.getSession();
        System.out.println("sessionId = " + session.getId());
        session.setAttribute(CacheKeys.Login.USER_ID.getCacheKey(), userId);
        //redisService.opsForValue().set("loginUser:" + userId, session.getId());

        return new ResultBean(true, "登录成功！");
    }
 
    /**
     * 
     * @description 
     * @param userId
     * @return
     * @author qianye.zheng
     */
    @GetMapping(value = "/get")
    public ResultBean get()
    {
        User user = new User();
        user.setId("123");
        user.setUsername("guangzhou");
        ResultBean resultBean = new ResultBean();
        resultBean.setData(user);
        
        return resultBean;
    }
}
