package com.keke.service.good;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keke.bean.good.Good;
import com.keke.dao.good.GoodDao;
import com.keke.exception.OrderServiceException;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
@Service("goodService") 
public class GoodService{ 
	private static Log log = LogFactory.getLog(GoodService.class); 
	@Autowired(required=true) 
	private GoodDao goodDao; 
	public Good saveGood (Good good) { 
		try { 
			goodDao.insertGood(good); 
			return good; 
		} catch (Exception e) {
			log.error("Failed to save system good, good="+ good,e); 
			throw new OrderServiceException("Failed to save system good, good="+ good,e);
		} 
 		 
	} 
	public  Good getGoodById(Integer id) { 
			return goodDao.getGoodById(id); 
	}  
	public Good updateGood (Good good) { 
		try { 
			goodDao.updateGoodById(good); 
			return good; 
		} catch (Exception e) {
			log.error("Failed to update system good, good="+ good,e); 
			throw new OrderServiceException("Failed to update system good, good="+ good,e);
		} 
 		 
	} 
	public boolean deleteGood (Integer id , Integer operator_id,Timestamp operate_time) { 
		try { 
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put(OrderConstants.KEY_ID, id); 
			map.put(OrderConstants.KEY_STATUS, OrderConstants.STATUS_UNAVAILABLE); 
			map.put(OrderConstants.KEY_OPERATOR, operator_id); 
			map.put(OrderConstants.KEY_OPERATE_TIME, operate_time); 
			goodDao.updateGoodStatusById(map); 
			return true; 
		} catch (Exception e) {
			throw new OrderServiceException("Failed to delete system Good, id="+ id,e);
		} 
 		 
	} 
	public int countAllGood (String keyword) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		return goodDao.countAllGood(map); 
	} 
	public List<Good> getAllGood (String keyword ,Integer pageNum,Integer pageCount) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		OrderUtil.putPageParam(map,pageNum,pageCount); 
		return goodDao.getAllGood(map); 
	} 
}