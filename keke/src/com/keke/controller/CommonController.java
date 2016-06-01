package com.keke.controller;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.json.MappingJacksonJsonView;

import com.keke.bean.ErrorCode;
import com.keke.bean.user.User;
import com.keke.exception.OrderErrorViewException;
import com.keke.util.ErrorCodeConstants;
import com.keke.util.OrderConstants;


@Controller
@RequestMapping("/")
public class CommonController {
	
	private static Log log = LogFactory.getLog(CommonController.class);

	@Autowired(required = true)
	private MessageSource messageSource;

	/**
	 * 
	 * spring-mcv Controller的封装类。 提供通用的返回json和vm视图方法。
	 * 
	 * @author liuxiaochun
	 * 
	 */
	
	/**
	 * 描述：首页登录页面login.vm
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView index(HttpServletRequest request) {
		ModelAndView view = returnVm(true, null, "login/login",
				ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}
	
	/**
	 * 返回json类型的视图
	 * 
	 * @param success
	 *            ：true或者false
	 * @param data
	 *            ：Object 对象。
	 * @param errorCode
	 *            ：错误码 @link ErrorCode。
	 * @param errorMsg
	 *            ：错误信息 @link ErrorCode。
	 * @return ModelAndView json视图，即json字符串。样例：{"data":{},"success":true}
	 * 
	 */
	public ModelAndView returnJson(boolean success, Object data, String error) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("data", data);
		String errorMsg = getI18nMsg(error);
		if (!StringUtils.isEmpty(errorMsg)) {
			ErrorCode errorCode = new ErrorCode(error, errorMsg);
			map.put("errorCode", errorCode.getErrorCode());
			map.put("errorMsg", errorCode.getErrorMsg());
		} else {
			map.put("errorCode", error);
		}
		ModelAndView view = new ModelAndView(new MappingJacksonJsonView(), map);
		return view;
	}

	/**
	 * 返回json类型的视图
	 * 
	 * @param success
	 *            ：true或者false
	 * @param data
	 *            ：Object 对象。
	 * @param errorCode
	 *            ：错误码 @link ErrorCode。
	 * @param errorMsg
	 *            ：错误信息 @link ErrorCode。
	 * @return ModelAndView json视图，即json字符串。包含count总数。
	 * 
	 */
	public ModelAndView returnJsonPage(boolean success, Object data, int count,
			String error) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
		map.put("data", data);
		map.put("count", count);
		String errorMsg = getI18nMsg(error);
		if (!StringUtils.isEmpty(errorMsg)) {
			ErrorCode errorCode = new ErrorCode(error, errorMsg);
			map.put("errorCode", errorCode.getErrorCode());
			map.put("errorMsg", errorCode.getErrorMsg());
		} else {
			map.put("errorCode", error);
		}
		ModelAndView view = new ModelAndView(new MappingJacksonJsonView(), map);
		return view;
	}

	/**
	 * 返回vm类型的视图
	 * 
	 * @param success
	 *            ：true或者false
	 * @param data
	 *            ：Object 对象。
	 * @param errorCode
	 *            ：错误码 @link ErrorCode。
	 * @param errorMsg
	 *            ：错误信息 @link ErrorCode。
	 * @param vmName
	 *            ：vm的视图名称。只写名称，不带.vm后缀。
	 * @return ModelAndView vm视图，需要前端处理显示。
	 * 
	 */
	public ModelAndView returnVm(boolean success, Object data, String vmName,
			String error) {
		ModelAndView view = new ModelAndView(vmName);
		view.addObject("success", success);
		view.addObject("data", data);
		String errorMsg = getI18nMsg(error);
		if (!StringUtils.isEmpty(errorMsg)) {
			ErrorCode errorCode = new ErrorCode(error, errorMsg);
			view.addObject("errorCode", errorCode.getErrorCode());
			view.addObject("errorMsg", errorCode.getErrorMsg());
		} else {
			view.addObject("errorCode", error);
		}
		return view;
	}

	/**
	 * 获取国际化信息 （主要用于错误码，客户端错误提示）
	 * 
	 * @param msgName
	 *            ：错误码名称
	 * @param params
	 *            ：要替换的参数 在properties 中用{1}代替
	 * @param language
	 *            ：本地语言环境
	 * @return String 经i18n国际化之后的信息
	 * 
	 */
	public String getI18nMsg(String msgName) {
		return messageSource.getMessage(msgName, null, Locale.CHINA);
	}

	public String getI18nMsg(String msgName, Object[] params) {
		return messageSource.getMessage(msgName, params, Locale.CHINA);
	}

	public String getI18nMsg(String msgName, Object[] params, Locale language) {
		return messageSource.getMessage(msgName, params, language);
	}

	
	/**
	 * 获得当前登录的用户信息
	 * 
	 * @param request
	 * @return user
	 * 
	 */
	public User getSessionUser(HttpServletRequest request) {
		User user = (User) request.getSession().getAttribute(
				OrderConstants.SESSION_LOGON_USER);
		return user;
	}

	/**
	 * 获得当前登录的用户id
	 * 
	 * @param request
	 * @return id
	 * 
	 */
	public int getSessionUserId(HttpServletRequest request) {
		int id = 0;
		User user = (User) request.getSession().getAttribute(
				OrderConstants.SESSION_LOGON_USER);
		if (user != null) {
			id = user.getId();
		}
		return id;
	}
	
	/**
	 * 判断用户是否登录
	 * 
	 * @param request
	 * @return ModelAndView 如果未登录，返回错误的json视图。
	 * 
	 */
	public ModelAndView isLogon(HttpServletRequest request) {
		ModelAndView view = null;
		User user = getSessionUser(request);
		if (user == null) {
			throw new OrderErrorViewException(false, "error.the user is not logon.",
					ErrorCodeConstants.ERROR_SYS_USER_NOT_LOGON);
		}
		return view;
	}
	
}