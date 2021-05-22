/**
 * 描述: 
 * PersonController.java
 * @author qye.zheng
 * 
 *  version 1.0
 */
package com.hua.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hua.annotation.AddCheck;
import com.hua.annotation.QueryCheck;
import com.hua.annotation.UpdateCheck;
import com.hua.bean.BusinessParamReq;
import com.hua.bean.PersonSearchBean;
import com.hua.bean.ResultBean;
import com.hua.bean.SomeQuery;

/**
 * 描述:
 * @author qye.zheng
 * 
 *         PersonController
 */
// 控制器
@RestController
@RequestMapping("/validate")
public class ValidateController extends BaseController {
    
    // @Value("${system.name}")
    // private String name;
    
	/**
     * 
     * 描述:
     * @author qye.zheng
     * 
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = { "/param-validate" })
    public ResultBean paramValidate(@RequestBody @Valid final BusinessParamReq req) {
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        ResultBean result = new ResultBean();
        result.setMessage("收到请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * 描述:
     * @author qye.zheng
     * 
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = { "/postNotInBody" })
    public ResultBean postNotInBody(final HttpServletRequest request, final HttpServletResponse response,
    		@RequestBody @Valid final PersonSearchBean searchBean) {
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        log.info("postNotInBody =====> name = " + searchBean.getName());
        log.info("postNotInBody =====> password = " + searchBean.getPassword());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * 描述:
     * @author qye.zheng
     * 
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = { "/postInBody" })
    public ResultBean postInBody(final HttpServletRequest request, final HttpServletResponse response,
            @RequestBody(required = true) final PersonSearchBean searchBean) {
        /*
         * @RequestBody 注解: 处理放在请求消息体中的报文，格式由客户端的Content-Type参数决定
         */
        log.info("postInBody =====> name = " + searchBean.getName());
        log.info("postInBody =====> password = " + searchBean.getPassword());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * 描述:
     * @author qye.zheng
     * 
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    // @RequestMapping(value={"/getAndPost"}, method = {RequestMethod.GET, RequestMethod.POST})
    @GetMapping(value = { "/getAndPost" })
    public ResultBean getAndPost(final HttpServletRequest request, final HttpServletResponse response,
            final PersonSearchBean searchBean) {
        log.info("getAndPost =====> name = " + searchBean.getName());
        log.info("getAndPost =====> password = " + searchBean.getPassword());
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * 描述:
     * @author qye.zheng
     * 
     * @param request
     * @param response
     * @param searchBean
     * @return
     */
    @PostMapping(value = { "/search" })
    public ResultBean search(final HttpServletRequest request, final HttpServletResponse response,
            @RequestBody(required = true) @Valid final PersonSearchBean searchBean) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + searchBean.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param code
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = { "/get/{code}" })
    public ResultBean get(@PathVariable final String code) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + code + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /*=================================================================================== */
    
    /**
     * 
     * @description
     * @param code
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = { "/required1/{code}" })
    public ResultBean param1(@PathVariable(required = true) final String code) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + code + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param code
     * @return
     * @author qianye.zheng
     */
    @GetMapping(path = { "/required2" })
    public ResultBean param2(@RequestParam(required = true) String code) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + code + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param param
     * @return
     * @author qianye.zheng
     */
    @PostMapping(path = { "/addCheck" })
    public ResultBean addCheck(@Validated(AddCheck.class) @RequestBody final SomeQuery param) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + param.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param param
     * @return
     * @author qianye.zheng
     */
    @PostMapping(path = { "/updateCheck" })
    public ResultBean updateCheck(@Validated(UpdateCheck.class) @RequestBody final SomeQuery param) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + param.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param param
     * @return
     * @author qianye.zheng
     */
    @PostMapping(path = { "/queryCheck" })
    public ResultBean queryCheck(@Validated(QueryCheck.class) @RequestBody final SomeQuery param) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + param.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    /**
     * 
     * @description
     * @param param
     * @return
     * @author qianye.zheng
     */
    @PostMapping(path = { "/multiCheck" })
    public ResultBean multiCheck(@Validated({QueryCheck.class, AddCheck.class}) @RequestBody final SomeQuery param) {
        ResultBean result = new ResultBean();
        result.setMessage("收到[" + param.getName() + "]的请求");
        result.setMessageCode("205");
        result.setSuccess(true);
        
        return result;
    }
    
    
    
}
