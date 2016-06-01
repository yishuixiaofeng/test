package com.keke.service.user;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.keke.bean.user.UserGood;
import com.keke.dao.user.UserGoodDao;
import com.keke.exception.OrderServiceException;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
@Service("userGoodService") 
public class UserGoodService{ 
	private static Log log = LogFactory.getLog(UserGoodService.class); 
	@Autowired(required=true) 
	private UserGoodDao userGoodDao; 
	public UserGood saveUserGood (UserGood userGood) { 
		try { 
			userGoodDao.insertUserGood(userGood); 
			return userGood; 
		} catch (Exception e) {
			log.error("Failed to save system userGood, userGood="+ userGood,e); 
			throw new OrderServiceException("Failed to save system userGood, userGood="+ userGood,e);
		} 
 		 
	} 
	public  UserGood getUserGoodById(Integer id) { 
			return userGoodDao.getUserGoodById(id); 
	}  
	public UserGood updateUserGood (UserGood userGood) { 
		try { 
			userGoodDao.updateUserGoodById(userGood); 
			return userGood; 
		} catch (Exception e) {
			log.error("Failed to update system userGood, userGood="+ userGood,e); 
			throw new OrderServiceException("Failed to update system userGood, userGood="+ userGood,e);
		} 
 		 
	} 
	public boolean deleteUserGood (Integer id , Integer operator_id,Timestamp operate_time) { 
		try { 
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put(OrderConstants.KEY_ID, id); 
			map.put(OrderConstants.KEY_STATUS, OrderConstants.STATUS_UNAVAILABLE); 
			map.put(OrderConstants.KEY_OPERATOR, operator_id); 
			map.put(OrderConstants.KEY_OPERATE_TIME, operate_time); 
			userGoodDao.updateUserGoodStatusById(map); 
			return true; 
		} catch (Exception e) {
			throw new OrderServiceException("Failed to delete system UserGood, id="+ id,e);
		} 
 		 
	} 
	public int countAllUserGood (String keyword,Integer type,Integer f_user_id) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		if(!OrderUtil.isNull(type)){ 
			map.put("type", type); 
		} 
		if(!OrderUtil.isNull(f_user_id)){ 
			map.put("f_user_id", f_user_id); 
		} 
		return userGoodDao.countAllUserGood(map); 
	} 
	public List<UserGood> getAllUserGood (String keyword ,Integer type,Integer f_user_id,Integer pageNum,Integer pageCount) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		if(!OrderUtil.isNull(type)){ 
			map.put("type", type); 
		} 
		if(!OrderUtil.isNull(f_user_id)){ 
			map.put("f_user_id", f_user_id); 
		} 
		OrderUtil.putPageParam(map,pageNum,pageCount); 
		return userGoodDao.getAllUserGood(map); 
	} 
}