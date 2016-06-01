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

import com.keke.bean.good.GoodType;
import com.keke.dao.good.GoodTypeDao;
import com.keke.exception.OrderServiceException;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
@Service("goodTypeService") 
public class GoodTypeService{ 
	private static Log log = LogFactory.getLog(GoodTypeService.class); 
	@Autowired(required=true) 
	private GoodTypeDao goodTypeDao; 
	public GoodType saveGoodType (GoodType goodType) { 
		try { 
			goodTypeDao.insertGoodType(goodType); 
			return goodType; 
		} catch (Exception e) {
			log.error("Failed to save system goodType, goodType="+ goodType,e); 
			throw new OrderServiceException("Failed to save system goodType, goodType="+ goodType,e);
		} 
 		 
	} 
	public  GoodType getGoodTypeById(Integer id) { 
			return goodTypeDao.getGoodTypeById(id); 
	}  
	public GoodType updateGoodType (GoodType goodType) { 
		try { 
			goodTypeDao.updateGoodTypeById(goodType); 
			return goodType; 
		} catch (Exception e) {
			log.error("Failed to update system goodType, goodType="+ goodType,e); 
			throw new OrderServiceException("Failed to update system goodType, goodType="+ goodType,e);
		} 
 		 
	} 
	public boolean deleteGoodType (Integer id , Integer operator_id,Timestamp operate_time) { 
		try { 
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put(OrderConstants.KEY_ID, id); 
			map.put(OrderConstants.KEY_STATUS, OrderConstants.STATUS_UNAVAILABLE); 
			map.put(OrderConstants.KEY_OPERATOR, operator_id); 
			map.put(OrderConstants.KEY_OPERATE_TIME, operate_time); 
			goodTypeDao.updateGoodTypeStatusById(map); 
			return true; 
		} catch (Exception e) {
			throw new OrderServiceException("Failed to delete system GoodType, id="+ id,e);
		} 
 		 
	} 
	public int countAllGoodType (String keyword) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		return goodTypeDao.countAllGoodType(map); 
	} 
	public List<GoodType> getAllGoodType (String keyword ,Integer pageNum,Integer pageCount) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		OrderUtil.putPageParam(map,pageNum,pageCount); 
		return goodTypeDao.getAllGoodType(map); 
	} 
}