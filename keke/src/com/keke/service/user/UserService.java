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

import com.keke.bean.user.User;
import com.keke.dao.user.UserDao;
import com.keke.exception.OrderServiceException;
import com.keke.util.OrderConstants;
import com.keke.util.OrderUtil;
@Service("userService") 
public class UserService{ 
	private static Log log = LogFactory.getLog(UserService.class); 
	@Autowired(required=true) 
	private UserDao userDao; 
	public User saveUser (User user) { 
		try { 
			userDao.insertUser(user); 
			return user; 
		} catch (Exception e) {
			log.error("Failed to save system user, user="+ user,e); 
			throw new OrderServiceException("Failed to save system user, user="+ user,e);
		} 
 		 
	} 
	public  User getUserById(Integer id) { 
			return userDao.getUserById(id); 
	}  
	public User getUserByName(String username) {
		User user = userDao.getUserByName(username);
		return user;
	}
	public User login(String username, String pwdhash) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(OrderConstants.KEY_USERNAME, username);
		map.put(OrderConstants.KEY_PWDHASH, pwdhash);
		return userDao.getUserLogin(map);
	}
	public User updateUser (User user) { 
		try { 
			userDao.updateUserById(user); 
			return user; 
		} catch (Exception e) {
			log.error("Failed to update system user, user="+ user,e); 
			throw new OrderServiceException("Failed to update system user, user="+ user,e);
		} 
 		 
	} 
	public boolean deleteUser (Integer id , Integer operator_id,Timestamp operate_time) { 
		try { 
			Map<String, Object> map = new HashMap<String, Object>(); 
			map.put(OrderConstants.KEY_ID, id); 
			map.put(OrderConstants.KEY_STATUS, OrderConstants.STATUS_UNAVAILABLE); 
			map.put(OrderConstants.KEY_OPERATOR, operator_id); 
			map.put(OrderConstants.KEY_OPERATE_TIME, operate_time); 
			userDao.updateUserStatusById(map); 
			return true; 
		} catch (Exception e) {
			throw new OrderServiceException("Failed to delete system User, id="+ id,e);
		} 
 		 
	} 
	public int countAllUser (String keyword) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){ 
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		return userDao.countAllUser(map); 
	} 
	public List<User> getAllUser (String keyword ,Integer pageNum,Integer pageCount) { 
		Map<String, Object> map = new HashMap<String, Object>(); 
		if(!StringUtils.isEmpty(keyword)){
			map.put(OrderConstants.KEY_KEY_WORD, keyword); 
		} 
		OrderUtil.putPageParam(map,pageNum,pageCount); 
		return userDao.getAllUser(map); 
	} 
}