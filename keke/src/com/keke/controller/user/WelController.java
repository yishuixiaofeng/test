package com.keke.controller.user;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.keke.controller.CommonController;
import com.keke.util.DateUtil;
import com.keke.util.ErrorCodeConstants;
import com.keke.util.OrderConstants;


@Controller
@RequestMapping("/index")
public class WelController extends CommonController {

	private static Log log = LogFactory.getLog(WelController.class);
	
	/**
	 * 描述：用户角色表新增信息
	 * 
	 * @param request
	 * @param f_role_id
	 * @param f_user_id
	 * @return
	 */
	@RequestMapping(value = "/welcome", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView welcome(HttpServletRequest request,
			String name)throws Exception {
		ModelAndView view = null;
		JSONObject json = new JSONObject();
		json.put("welcome", "欢迎");
		json.put("name", name);
		
		view = returnJson(true, json, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;

	}

}
