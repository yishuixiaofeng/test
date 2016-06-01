package com.keke.dao.user;

 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.keke.bean.user.UserGood;
import com.keke.dao.CommonDao;
@Component 
public class UserGoodDao extends CommonDao{
	public int insertUserGood (UserGood userGood) { 
		return getSqlSession().insert("insertUserGood", userGood); 
	} 
	public UserGood getUserGoodById(int id)  { 
		return getSqlSession().selectOne("getUserGoodById", id); 
	} 
	public int updateUserGoodById (UserGood userGood) { 
		return getSqlSession().update("updateUserGoodById", userGood); 
	} 
	public int deleteUserGoodById (int id) { 
		return getSqlSession().delete("deleteUserGoodById", id); 
	} 
	public int countAllUserGood (Map<String, Object> map) { 
		return getSqlSession().selectOne("countAllUserGood", map); 
	} 
	public List<UserGood> getAllUserGood (Map<String, Object> map) { 
		return getSqlSession().selectList("getAllUserGood", map); 
	}
	public int updateUserGoodStatusById (Map<String, Object> map) { 
		return getSqlSession().update("updateUserGoodStatusById", map); 
	} 
}