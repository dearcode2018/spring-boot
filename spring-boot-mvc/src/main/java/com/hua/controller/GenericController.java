/**
  * @filename GenericController.java
  * @description 
  * @version 1.0
  * @author qianye.zheng
 */
package com.hua.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.hua.bean.PersonSearchBean;
import com.hua.bean.ResultBean;
import com.hua.entity.User;
import com.hua.util.JacksonUtil;

/**
 * @type GenericController
 * @description 
 * @author qianye.zheng
 */
@RestController
@RequestMapping("/generic")
public class GenericController extends BaseController {
    
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = "/v1/postKeyValue")
    @ResponseBody
    public ResultBean postKeValue(final HttpServletRequest request, 
            final HttpServletResponse response, final PersonSearchBean searchBean) {
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        log.info("postKeValue =====> name = " + searchBean.getName());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        User user = new User();
        user.setId("123");
        user.setUsername("张三");
        user.setPassword("ollldd");
        result.setData(user);
        
        return result;
    }
    
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = "/v2/postKeyValue")
    @ResponseBody
    public ResultBean postKeValueV2(final HttpServletRequest request, 
            final HttpServletResponse response) {
        // x-www-form-urlencoded
        final Map<String, String[]> map = request.getParameterMap();
        final Map<String, Object> resultMap = new HashMap<>(map.size());
        final Set<Map.Entry<String, String[]>> entrySet = map.entrySet();
        for (Map.Entry<String, String[]> e : entrySet) {
            resultMap.put(e.getKey(), e.getValue()[0]);
        }
        map.clear();
        System.out.println(JacksonUtil.writeAsString(resultMap));
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        final PersonSearchBean searchBean = new PersonSearchBean();
        System.out.println(request.getParameter("name"));
        log.info("postKeValue =====> name = " + searchBean.getName());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        User user = new User();
        user.setId("123");
        user.setUsername("张三");
        user.setPassword("ollldd");
        result.setData(user);
        
        return result;
    }
    
    /**
     * 
     * 描述: 
     * @author qye.zheng
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = "/v1/postJson")
    @ResponseBody
    public ResultBean postJsonV1(final HttpServletRequest request, 
            final HttpServletResponse response, @RequestBody  String body) {
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        final PersonSearchBean searchBean = JacksonUtil.readValue(body, PersonSearchBean.class);
        System.out.println(request.getParameter("name"));
        log.info("postKeValue =====> name = " + searchBean.getName());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        User user = new User();
        user.setId("123");
        user.setUsername("张三");
        user.setPassword("ollldd");
        result.setData(user);
        
        return result;
    }
    
}
