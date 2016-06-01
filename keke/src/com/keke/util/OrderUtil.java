package com.keke.util;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.keke.exception.OrderErrorViewException;

public class OrderUtil {

	private static Log log = LogFactory.getLog(OrderUtil.class);

	/**
	* 计算分页常量
	* @param currentPage ：当前页数
	* @param totalCount ：总数
	* @param pageCount ：每页数目
	* @return Pagination 分页常量类。
	* 
	*/
	public static Pagination createPagination(Integer currentPage, Integer totalCount, Integer pageCount)
	{
		Pagination pagination = new Pagination();
		if(pageCount == null || pageCount == 0){
			currentPage = 1;
			pageCount = 0;
			
		}
		else{
			if(currentPage != null &&  currentPage >= 0 ){
				currentPage += 1;
			}
			int totalPage = (totalCount % pageCount ==0 ?totalCount/pageCount :totalCount/pageCount+1);
			if(totalPage > 0){
				currentPage = currentPage <= totalPage ?currentPage:totalPage;
			}
			
		}
		pagination.setCurrentPage(currentPage);
		pagination.setPageCount(pageCount);
		pagination.setTotalCount(totalCount);
		return pagination;
	}
	
	/**
	* 当String参数为空时获取默认值。为空，取默认值，不为空，参数值。
	* @param value ：参数值
	* @param defaultValue ：默认值
	* @return 为空，取默认值，不为空，参数值。
	* 
	*/
	public static String getDefValueIfNull(String value,String defaultValue)
	{
		if(StringUtils.isEmpty(value)){
			return defaultValue;
		}
		return value;
	}

	/**
	* 当Integer参数为空时获取默认值。为空或者小于等于0，取默认值，不为空，参数值。
	* @param value ：参数值
	* @param defaultValue ：默认值
	* @return 为空，取默认值，不为空，参数值。
	* 
	*/
	public static Integer getDefValueIfNull(Integer value,int defaultValue)
	{
		if(value == null || value <= 0){
			return defaultValue;
		}
		return value;
	}
	
	/**
	* 判断字符串是否为空
	* @param value ：参数值
	* @return 为空，true，不为空，false。
	* 
	*/
	public static boolean isNull(String value)
	{
		if(StringUtils.isEmpty(value)){
			return true;
		}
		return false;
	}
	
	/**
	* 判断Integer是否为空
	* @param value ：参数值
	* @return 为空或者等于0，true，不为空，false。
	* 
	*/
	public static boolean isNull(Integer value)
	{
		if(value == null || value == 0){
			return true;
		}
		return false;
	}
	
	/**
	* 判断List是否为空
	* @param value ：参数值
	* @return 为空或者长度为0，true，不为空，false。
	* 
	*/
	public static boolean isNull(List<?> value)
	{
		if(value == null || value.size() == 0){
			return true;
		}
		return false;
	}
	
	/**
	* 判断Map是否为空
	* @param value ：参数值
	* @return 为空或者长度为0，true，不为空，false。
	* 
	*/
	public static boolean isNull(Map<String, Object> value)
	{
		if(value == null || value.size() == 0){
			return true;
		}
		return false;
	}
	
	/**
	* 字符串转int，如果出错，返回默认值
	* @param value ：整数型字符串
	* @param defaultValue ：默认int值
	* @return 
	* 
	*/
	public static int toInt(String value,int defaultValue)
	{
		return NumberUtils.toInt(value, defaultValue);
	}

	public static void putPageParam(Map<String, Object> map, Integer pageNum,
			Integer pageCount) {
		if(!isNull(pageNum) && !isNull(pageCount)){
			Integer offset = (pageNum - 1) * pageCount;
			map.put(OrderConstants.KEY_OFFSET, offset);
			map.put(OrderConstants.KEY_PAGE_COUNT, pageCount);
		}
		
	}
	
	/**
	* 获得属于第一个集合，不属于第二个集合的元素列表。也就是第一个集合与第二个集合的差值集合
	* 如果是自定义类型，需要实现hashCode和equal方法
	* @param all ：总集合
	* @param part ：部分集合
	* @return 在总集合中不在部分集合的元素列表
	* 
	*/
	public static List getDifferentList(List all,List part) {
		List list  = new ArrayList();
		if(!OrderUtil.isNull(all) && !OrderUtil.isNull(part)){
			for (Object object : all) {
				if(!part.contains(object)){
					list.add(object);
				}
			}
		}
		if(OrderUtil.isNull(part)){
			list =all;
		}
		return list;
	}
	
	/**
	* 获得属于第一个集合，同时属于第二个集合的元素列表。也就是第一个集合与第二个集合的交集
	* 如果是自定义类型，需要实现hashCode和equal方法
	* @param all ：总集合
	* @param part ：部分集合
	* @return 在总集合中也在部分集合的元素列表
	* 
	*/
	public static List getSameList(List all,List part) {
		List list  = new ArrayList();
		if(!OrderUtil.isNull(all) && !OrderUtil.isNull(part)){
			for (Object object : all) {
				if(part.contains(object)){
					list.add(object);
				}
			}
		}
		return list;
	}
	
	/**
	 * 计算两个数的百分比
	 * @param m
	 * @param n
	 * @return String
	 */
	public static String   getPercent(long m,long n) {
		NumberFormat format = NumberFormat.getPercentInstance();// 获取格式化类实例
        format.setMinimumFractionDigits(2);// 设置小数位
        return format.format(m /n *100);// 打印计算结果
	}
	
	/**
	 * 校验对象是否为空，若为空，返回错误视图提示。
	 * @param o 对象
	 * @param errorMsg 错误信息
	 * @param errorCode 错误码
	 * @return String
	 */
	public static void  validateNull(Object o,String errorMsg,String errorCode) {
		if(o == null){
			log.error("error.NullPointerException.errorCode="+errorCode+",errorMsg="+errorMsg);
			throw new OrderErrorViewException(false, errorMsg,errorCode);
		}
	}
}
