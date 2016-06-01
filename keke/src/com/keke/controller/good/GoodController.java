package com.keke.controller.good;

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
import com.keke.controller.CommonController;
import com.keke.exception.OrderErrorViewException;
import com.keke.service.good.GoodService;
import com.keke.util.DateUtil;
import com.keke.util.ErrorCodeConstants;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
import com.keke.util.Pagination;

@Controller
@RequestMapping("good")
public class GoodController extends CommonController {

	private static Log log = LogFactory.getLog(GoodController.class);

	@Autowired(required = true)
	private GoodService goodService;
	


	/**
	 * 判断参数Good是否存在
	 * 
	 * @param name
	 * @return ModelAndView 如果name为空，返回错误的json视图。
	 * 
	 */
	public ModelAndView isGoodFound(Good good, Integer id) {
		ModelAndView view = null;
		// 业务逻辑校验
		if (good == null) {
			log.error("error.the good is not found.id=" + id);
			throw new OrderErrorViewException(false, "error.the good is not found.id=" + id,
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
	@RequestMapping(value = "/getGoodById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getGood(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		
		
		Good good = goodService.getGoodById(id);
		ModelAndView view  = returnJson(true, good, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}


	/**
	 * 描述连表查询good和goodRoles
	 * 
	 * @param request
	 * @param f_role_id
	 * @param keyword
	 * @param currentPage
	 * @param pageCount
	 * @return
	 */
	@RequestMapping(value = "/getGoodList", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView getGoodList(HttpServletRequest request,
			String keyword, Integer currentPage,
			Integer pageCount)throws Exception {
		
		Integer totalCount = goodService.countAllGood(keyword);
		Pagination pagination = OrderUtil.createPagination(currentPage,
				totalCount, pageCount);
		List<Good> uu = goodService.getAllGood(keyword, pagination.getCurrentPage(), pagination.getPageCount());
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
	@RequestMapping(value = "/saveGood", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView saveGood(HttpServletRequest request, Integer type,
			String name, Double price, Integer stock,String description,String company) throws Exception {
		//http://123.206.90.124/keke/good/saveGood.do?name=oppR9&price=3234.56&
		//stock=20&description=跑男独家&company=opp
		
		if (StringUtils.isEmpty(name)) {
			log.error("error.name  is not null.name=" + name );
			throw new OrderErrorViewException(false, "error.name  is not null.name=" + name, 
					ErrorCodeConstants.ERROR_SYS_NAME_IS_NULL);
		}
		
		Good good = new Good();
		good.setCreattime(DateUtil.getCurrentTimestamp());
		good.setDescription(description);
		good.setName(name);
		good.setPrice(price);
		good.setStock(stock);
		good.setType(type);
		good.setCompany(company);
		good = goodService.saveGood(good);
		
		ModelAndView view = returnJson(true, good, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}

	/**
	 * 描述：根据用户id单查用户信息
	 * 
	 * @param request
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/deleteGoodById", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView deleteGoodById(HttpServletRequest request,Integer id)throws Exception{
		// 管理员权限校验，如果不为空，则是错误视图，直接返回给客户端。
		

		Good good = goodService.getGoodById(id);
		isGoodFound(good, id);
		goodService.deleteGood(id, 0, null);
		ModelAndView view = returnJson(true, true, ErrorCodeConstants.ERROR_SYS_SUCCESS);
		return view;
	}
}