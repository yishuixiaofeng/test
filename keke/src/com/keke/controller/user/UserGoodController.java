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

import com.keke.bean.user.UserGood;
import com.keke.controller.CommonController;
import com.keke.exception.OrderErrorViewException;
import com.keke.service.user.UserGoodService;
import com.keke.util.DateUtil;
import com.keke.util.ErrorCodeConstants;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
import com.keke.util.Pagination;

@Controller
@RequestMapping("usergood")
public class UserGoodController extends CommonController {

	private static Log log = LogFactory.getLog(UserGoodController.class);

	@Autowired(required = true)
	private UserGoodService userGoodService;
	


	/**
	 * 判断参数UserGood是否存在
	 * 
	 * @param name
	 * @return ModelAndView 如果name为空，返回错误的json视图。
	 * 
	 */
	public ModelAndView isUserGoodFound(UserGood userGood, Integer id) {
		ModelAndView view = null;
		// 业务逻辑校验
		if (userGood == null) {
			log.error("error.the userGood is not found.id=" + id);
			throw new OrderErrorViewException(false, "error.the userGood is not found.id=" + id,
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
	@RequestMapping(value = "/getUserGoodById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getUserGood(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
		
		UserGood userGood = userGoodService.getUserGoodById(id);
		ModelAndView view  = returnJson(true, userGood, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}


	/**
	 * 描述连表查询userGood和userGoodRoles
	 * 
	 * @param request
	 * @param f_role_id
	 * @param keyword
	 * @param currentPage
	 * @param pageCount
	 * @return
	 */
	@RequestMapping(value = "/getUserGoodList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getUserGoodList(HttpServletRequest request,
			String keyword, Integer type,Integer f_user_id,Integer currentPage,
			Integer pageCount)throws Exception {
		
		Integer totalCount = userGoodService.countAllUserGood(keyword,type,f_user_id);
		Pagination pagination = OrderUtil.createPagination(currentPage,
				totalCount, pageCount);
		List<UserGood> uu = userGoodService.getAllUserGood(keyword,type,f_user_id, pagination.getCurrentPage(), pagination.getPageCount());
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
	@RequestMapping(value = "/saveShopCart", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveUserGood(HttpServletRequest request, Integer f_good_id,
		Integer	f_user_id, Integer buyCount, Double totalMoney,Double finalMoney,String description) throws Exception {
		//http://123.206.90.124/keke/userGood/saveUserGood.do?name=oppR9&price=3234.56&
		//stock=20&description=跑男独家&company=opp
		
		if (OrderUtil.isNull(f_good_id)||OrderUtil.isNull(f_user_id)) {
			log.error("error.f_user_id  is not null.f_user_id=" + f_user_id );
			throw new OrderErrorViewException(false, "error.name  is not null.f_user_id=" + f_user_id, 
					ErrorCodeConstants.ERROR_SYS_ID_IS_NULL);
		}
		UserGood userGood = createUserGood(f_good_id,f_user_id,buyCount,totalMoney,finalMoney,description);
		userGood.setType(OrderConstants.TYPE_USER_GOOD_SHOP_CART);
		userGood = userGoodService.saveUserGood(userGood);
		
		ModelAndView view = returnJson(true, userGood, ErrorCodeConstants.ERROR_SYS_SUCCESS);
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
	@RequestMapping(value = "/saveMyOrder", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveMyOrder(HttpServletRequest request, Integer f_good_id,
		Integer	f_user_id, Integer buyCount, Double totalMoney,Double finalMoney,String description) throws Exception {
		//http://123.206.90.124/keke/userGood/saveUserGood.do?name=oppR9&price=3234.56&
		//stock=20&description=跑男独家&company=opp
		
		if (OrderUtil.isNull(f_good_id)||OrderUtil.isNull(f_user_id)) {
			log.error("error.f_user_id  is not null.f_user_id=" + f_user_id );
			throw new OrderErrorViewException(false, "error.name  is not null.f_user_id=" + f_user_id, 
					ErrorCodeConstants.ERROR_SYS_ID_IS_NULL);
		}
		UserGood userGood = createUserGood(f_good_id,f_user_id,buyCount,totalMoney,finalMoney,description);
		userGood.setType(OrderConstants.TYPE_USER_GOOD_MY_ORDER);
		userGood = userGoodService.saveUserGood(userGood);
		
		ModelAndView view = returnJson(true, userGood, ErrorCodeConstants.ERROR_SYS_SUCCESS);
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
	@RequestMapping(value = "/saveMyStore", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveMyStore(HttpServletRequest request, Integer f_good_id,
		Integer	f_user_id, Integer buyCount, Double totalMoney,Double finalMoney,String description) throws Exception {
		//http://123.206.90.124/keke/userGood/saveUserGood.do?name=oppR9&price=3234.56&
		//stock=20&description=跑男独家&company=opp
		
		if (OrderUtil.isNull(f_good_id)||OrderUtil.isNull(f_user_id)) {
			log.error("error.f_user_id  is not null.f_user_id=" + f_user_id );
			throw new OrderErrorViewException(false, "error.name  is not null.f_user_id=" + f_user_id, 
					ErrorCodeConstants.ERROR_SYS_ID_IS_NULL);
		}
		UserGood userGood = createUserGood(f_good_id,f_user_id,buyCount,totalMoney,finalMoney,description);
		userGood.setType(OrderConstants.TYPE_USER_GOOD_MY_STORE);
		userGood = userGoodService.saveUserGood(userGood);
		
		ModelAndView view = returnJson(true, userGood, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}

	private UserGood createUserGood(Integer f_good_id, Integer f_user_id,
			Integer buyCount, Double totalMoney, Double finalMoney,
			String description) {
		UserGood userGood = new UserGood();
		userGood.setCreattime(DateUtil.getCurrentTimestamp());
		userGood.setDescription(description);
		userGood.setBuyCount(buyCount);
		userGood.setF_good_id(f_good_id);
		userGood.setF_user_id(f_user_id);
		userGood.setFinalMoney(finalMoney);
		userGood.setTotalMoney(totalMoney);
		return userGood;
	}

	/**
	 * 描述：根据用户id单查用户信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteUserGoodById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteUserGoodById(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		

		UserGood userGood = userGoodService.getUserGoodById(id);
		isUserGoodFound(userGood, id);
		userGoodService.deleteUserGood(id, 0, null);
		ModelAndView view = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}
}