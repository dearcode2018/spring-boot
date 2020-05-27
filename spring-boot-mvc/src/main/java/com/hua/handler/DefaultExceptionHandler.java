package com.hua.handler;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.hua.bean.ResultBean;
import com.hua.constant.Constant;
import com.hua.controller.BaseController;
import com.hua.util.JacksonUtil;

/**
 * 
 * @type DefaultExceptionHandler
 * @description 默认异常处理器
 * @author qianye.zheng
 */
@Service
public class DefaultExceptionHandler implements HandlerExceptionResolver {

	private static Logger logger = LoggerFactory.getLogger(DefaultExceptionHandler.class);
	
	
	/**
	 * 
	 * @description 
	 * @param request
	 * @param response
	 * @param obj
	 * @param exp
	 * @return
	 * @author qianye.zheng
	 */
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object obj, Exception exp) {
		logger.error("local-请求异常, URL:{} ", request.getRequestURI(), exp);
		final ResultBean resultBean = new ResultBean();
		resultBean.setSuccess(false);
		resultBean.setMessage("系统异常");
		response.setCharacterEncoding(Constant.CHART_SET_UTF_8);
		response.setContentType("application/json;charset=UTF-8");
		// 请求参数序列化后，存储到指定位置
		if (BaseController.getParams().isEmpty()) { // 参数为空或被忽略，切面有开关，无需提示，避免造成信息误会
			//System.out.println("请求参数为空");
		} else if (1 == BaseController.getParams().size()) { // 单个参数，直接存储
			System.out.println("请求参数: " + JacksonUtil.writeAsString(BaseController.getParams().get(0)));
		} else { // 多个参数，用数组形式
			System.out.println("请求参数: " + JacksonUtil.writeAsString(BaseController.getParams()));
		}

		// 处理完后，主动清理掉
		BaseController.clearParams();
		try
		{
			response.getWriter().write(JacksonUtil.writeAsString(resultBean));
		} catch (IOException e)
		{
			logger.error("异常: {}", e);
		}
		final ModelAndView model = new ModelAndView();
		
		return model;
	}
	
}
