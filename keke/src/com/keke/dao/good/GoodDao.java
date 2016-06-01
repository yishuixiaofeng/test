package com.keke.dao.good;

 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.keke.bean.good.Good;
import com.keke.dao.CommonDao;
@Component 
public class GoodDao extends CommonDao{
	public int insertGood (Good good) { 
		return getSqlSession().insert("insertGood", good); 
	} 
	public Good getGoodById(int id)  { 
		return getSqlSession().selectOne("getGoodById", id); 
	} 
	public int updateGoodById (Good good) { 
		return getSqlSession().update("updateGoodById", good); 
	} 
	public int deleteGoodById (int id) { 
		return getSqlSession().delete("deleteGoodById", id); 
	} 
	public int countAllGood (Map<String, Object> map) { 
		return getSqlSession().selectOne("countAllGood", map); 
	} 
	public List<Good> getAllGood (Map<String, Object> map) { 
		return getSqlSession().selectList("getAllGood", map); 
	}
	public int updateGoodStatusById (Map<String, Object> map) { 
		return getSqlSession().update("updateGoodStatusById", map); 
	} 
}