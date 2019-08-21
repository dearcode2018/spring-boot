/**
  * @filename RedisSessionInterceptor.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.interceptor;

import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.hua.bean.ResultBean;
import com.hua.util.JacksonUtil;

/**
 * @type RedisSessionInterceptor
 * @description 
 * @author qianye.zheng
 */
public class RedisSessionInterceptor implements HandlerInterceptor
{

	@Resource
	private StringRedisTemplate firstRedisTemplate;
	
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
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception
	{
		 //无论访问的地址是不是正确的，都进行登录验证，登录成功后的访问再进行分发，404的访问自然会进入到错误控制器中
        HttpSession session = request.getSession();
        if (session.getAttribute("loginUserId") != null)
        {
        	System.out.println( session.getAttribute("loginUserId"));
            try
            {
                //验证当前请求的session是否是已登录的session
                String loginSessionId = firstRedisTemplate.opsForValue().get("loginUser:" + (Integer) session.getAttribute("loginUserId"));
                if (loginSessionId != null && loginSessionId.equals(session.getId()))
                {
                    return true;
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
 
        response401(response);
        return false;
	}
	
	/**
	 * 
	 * @description 
	 * @param response
	 * @author qianye.zheng
	 */
	private void response401(HttpServletResponse response)
    {
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
 
        try
        {
            response.getWriter().write(JacksonUtil.writeAsString(new ResultBean(false, "用户未登录")));
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
 
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception
    {
 
    }
 
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception
    {
 
    }
	
}
