package com.keke.controller.user;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.keke.bean.good.Good;
import com.keke.bean.user.User;
import com.keke.controller.CommonController;
import com.keke.exception.OrderErrorViewException;
import com.keke.service.user.UserService;
import com.keke.util.DateUtil;
import com.keke.util.ErrorCodeConstants;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
import com.keke.util.Pagination;

@Controller
@RequestMapping("user")
public class UserUIController extends CommonController {

	private static Log log = LogFactory.getLog(UserUIController.class);

	@Autowired(required = true)
	private UserService userService;
	


	/**
	 * 修改密码
	 * @param request
	 * @param id
	 * @param originalPwd
	 * @param newPwd
	 * @return
	 */
//	@RequestMapping(value = "/updateUserPwd", method = RequestMethod.POST)
//	@ResponseBody
//	public ModelAndView updateUserPwd(HttpServletRequest request,String originalPwd,String newPwd)throws Exception{
//		ModelAndView view = null;
//		view = isLogon(request);
//		String originalPwdMd5 = MD5Util.string2MD5(originalPwd);
//		String newPwdMd5 = MD5Util.string2MD5(newPwd);
//	
//	   User user = getSessionUser(request);
//	   if(!user.getPwdhash().equals(originalPwdMd5)){
//			log.error("error.old pwd is not correct.Pwdhash=" + originalPwdMd5);
//			throw new OrderErrorViewException(false, "error.old pwd is not correct.Pwdhash=" + originalPwdMd5,
//					ErrorCodeConstants.ERROR_USER_OLD_PWD_WRONG);
//	   } 
//	   userService.updatePwd(user.getId(), newPwdMd5);
//	   view = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
//	   return view;
//	}
	/**
	 * 用户退出操作
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logout(HttpServletRequest request)throws Exception{
		
		request.getSession().invalidate();
		ModelAndView view = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
		
	}

	/**
	 * 判断参数User是否存在
	 * 
	 * @param name
	 * @return ModelAndView 如果name为空，返回错误的json视图。
	 * 
	 */
	public ModelAndView isUserFound(User user, Integer id) {
		ModelAndView view = null;
		// 业务逻辑校验
		if (user == null) {
			log.error("error.the user is not found.id=" + id);
			throw new OrderErrorViewException(false, "error.the user is not found.id=" + id,
					ErrorCodeConstants.ERROR_USER_IS_NOT_FOUND);
		}
		return view;
	}

	/**
	 * 描述：根据用户id单查用户信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getUser(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
	
		User user = userService.getUserById(id);
		ModelAndView view  = returnJson(true, user, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}

	/**
	 * 描述：用户登录
	 * 
	 * @param request
	 * @param username
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "/logon", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView logon(HttpServletRequest request, String name,
			String pwd)throws Exception {
		ModelAndView view = null;
		User user = userService.login(name, pwd);
		if(user == null){
			throw new OrderErrorViewException(false, "error.user logon failed.name="+name+",pwd="+pwd, 
					ErrorCodeConstants.ERROR_USER_NAME_PWD_WRONG);
		}
		request.getSession().setAttribute(OrderConstants.SESSION_LOGON_USER,
				user);
		view = returnJson(true, user,ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}

	/**
	 * 描述连表查询user和userRoles
	 * 
	 * @param request
	 * @param f_role_id
	 * @param keyword
	 * @param currentPage
	 * @param pageCount
	 * @return
	 */
	@RequestMapping(value = "/getUserList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getUserList(HttpServletRequest request,
			String keyword, Integer currentPage,
			Integer pageCount)throws Exception {
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
		
		Integer totalCount = userService.countAllUser(keyword);
		Pagination pagination = OrderUtil.createPagination(currentPage,
				totalCount, pageCount);
		List<User> uu = userService.getAllUser(keyword, pagination.getCurrentPage(), pagination.getPageCount());
		ModelAndView view = returnJsonPage(true, uu, totalCount,
				ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;

	}
	
	/**
	 * 描述：新增用户
	 * 
	 * @param request
	 * @param
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value = "/regist", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView regist(HttpServletRequest request, 
			String name, String pwd, Integer age,String description) throws Exception {
		
		
		if (StringUtils.isEmpty(name)) {
			log.error("error.name  is not null.name=" + name );
			throw new OrderErrorViewException(false, "error.name  is not null.name=" + name, 
					ErrorCodeConstants.ERROR_SYS_NAME_IS_NULL);
		}
		
		User user = userService.getUserByName(name);
		if (user != null) {
			log.error("error.user  is exit.name=" + name );
			throw new OrderErrorViewException(false, "error.user  is exit.name=" + name, 
					ErrorCodeConstants.ERROR_SYS_DATA_IS_EXIT);
		}
		user = new User();
		user.setName(name);
		user.setPwd(pwd);
		user.setAge(age);
		user.setDescription(description);
		
		user.setCreattime(DateUtil.getCurrentTimestamp());
		user = userService.saveUser(user);
		
		ModelAndView view = returnJson(true, user, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}
	
	/**
	 * 描述：根据用户id单查用户信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteUserById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteGoodById(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
		
		User user = userService.getUserById(id);
		isUserFound(user, id);
		userService.deleteUser(id, 0, null);
		ModelAndView view  = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}
	
	/**
	 * 描述：根据用户id单查用户信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/validateName", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView validateName(HttpServletRequest request,String name)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
		
		User user = userService.getUserByName(name);
		if (user != null) {
			log.error("error.user  is exit.name=" + name );
			throw new OrderErrorViewException(false, "error.user  is exit.name=" + name, 
					ErrorCodeConstants.ERROR_SYS_DATA_IS_EXIT);
		}
		ModelAndView view  = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}

}