package com.keke.dao.user;

 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.keke.bean.user.User;
import com.keke.dao.CommonDao;
@Component 
public class UserDao extends CommonDao{
	public User getUserLogin(Map<String, Object> map) {
		return getSqlSession().selectOne("getUserLogin", map);
	}
	public int insertUser (User user) { 
		return getSqlSession().insert("insertUser", user); 
	} 
	public User getUserById(int id)  { 
		return getSqlSession().selectOne("getUserById", id); 
	} 
	public User getUserByName(String username) {
		return getSqlSession().selectOne("getUserByName", username);
	}

	public int updateUserById (User user) { 
		return getSqlSession().update("updateUserById", user); 
	} 
	public int deleteUserById (int id) { 
		return getSqlSession().delete("deleteUserById", id); 
	} 
	public int countAllUser (Map<String, Object> map) { 
		return getSqlSession().selectOne("countAllUser", map); 
	} 
	public List<User> getAllUser (Map<String, Object> map) { 
		return getSqlSession().selectList("getAllUser", map); 
	}
	public int updateUserStatusById (Map<String, Object> map) { 
		return getSqlSession().update("updateUserStatusById", map); 
	} 
}