package com.keke.dao.good;

 
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.keke.bean.good.GoodType;
import com.keke.dao.CommonDao;
@Component 
public class GoodTypeDao extends CommonDao{
	public int insertGoodType (GoodType goodType) { 
		return getSqlSession().insert("insertGoodType", goodType); 
	} 
	public GoodType getGoodTypeById(int id)  { 
		return getSqlSession().selectOne("getGoodTypeById", id); 
	} 
	public int updateGoodTypeById (GoodType goodType) { 
		return getSqlSession().update("updateGoodTypeById", goodType); 
	} 
	public int deleteGoodTypeById (int id) { 
		return getSqlSession().delete("deleteGoodTypeById", id); 
	} 
	public int countAllGoodType (Map<String, Object> map) { 
		return getSqlSession().selectOne("countAllGoodType", map); 
	} 
	public List<GoodType> getAllGoodType (Map<String, Object> map) { 
		return getSqlSession().selectList("getAllGoodType", map); 
	}
	public int updateGoodTypeStatusById (Map<String, Object> map) { 
		return getSqlSession().update("updateGoodTypeStatusById", map); 
	} 
}