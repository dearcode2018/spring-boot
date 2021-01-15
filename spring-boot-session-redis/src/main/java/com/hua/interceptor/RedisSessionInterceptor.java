/**
  * @filename RedisSessionInterceptor.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hua.bean.ResultBean;
import com.hua.constant.CacheKeys;
import com.hua.util.JacksonUtil;

/**
 * @type RedisSessionInterceptor
 * @description
 * @author qianye.zheng
 */
public class RedisSessionInterceptor implements HandlerInterceptor {
    
    @Resource
    private StringRedisTemplate redisService;
    
    /**
     * @description
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     * @author qianye.zheng
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
        // 不要创建新的会话，会话失效之后，则无法根据cookie获取对应的会话对象
        
        //HttpSession session = request.getSession(false);
        HttpSession session = request.getSession();
        if (null == session) {
            System.out.println("未登录");
            
            return false;
        }
        Long userId = 123L;
        session.setAttribute(CacheKeys.Login.USER_ID.getCacheKey(), userId);
        //session.setAttribute("id-name", "current-name-gz2111");
        
        /*
         * Cookie[] cookies = request.getCookies();
         * for (Cookie cookie : cookies) {
         * System.out.println(cookie.getName() + ":" + cookie.getValue());
         * }
         */
        userId = (Long) session.getAttribute(CacheKeys.Login.USER_ID.getCacheKey());
        //System.out.println("sessionId = " + session.getId() + ", userId = " + userId);
        if (null != session.getId()) {
            return true;
        }
        /*
         * if (session.getAttribute(CacheKeys.Login.USER_ID.getCacheKey()) != null) {
         * System.out.println( session.getAttribute(CacheKeys.Login.USER_ID.getCacheKey()));
         * try
         * {
         * // 验证当前请求的session是否是已登录的session
         * String loginSessionId = redisService.opsForValue().get("loginUser:" + (Long)
         * session.getAttribute(CacheKeys.Login.USER_ID.getCacheKey()));
         * if (loginSessionId != null && loginSessionId.equals(session.getId()))
         * {
         * return true;
         * }
         * }
         * catch (Exception e)
         * {
         * e.printStackTrace();
         * }
         * }
         */
        
        response401(response);
        return false;
    }
    
    /**
     * 
     * @description
     * @param response
     * @author qianye.zheng
     */
    private void response401(HttpServletResponse response) {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        
        try {
            response.getWriter().write(JacksonUtil.writeAsString(new ResultBean(false, "用户未登录")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        
    }
    
}
