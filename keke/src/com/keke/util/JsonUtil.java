package com.keke.util;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import net.sf.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonUtil {

	private static Gson gson = null;
	static {
		if (gson == null) {
			gson = new Gson();
		}
	}

	private JsonUtil() {
	}

	/**
	 * 将对象转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String objectToJson(Object ts) {
		String jsonStr = null;
		if (gson != null) {
			jsonStr = gson.toJson(ts);
		}
		return jsonStr;
	}

	/**
	 * List转为json
	 * 
	 * @param list
	 * @return
	 */
	public static String list2json(List<?> list) {
		Gson gson = new Gson();
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			for (Object obj : list) {
				json.append(gson.toJson(obj));
				json.append(",");
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * List转为json
	 * 
	 * @param list
	 * @return
	 */
	public static String list2json2(List<?> list, int maxrecord) {
		Gson gson = new Gson();
		StringBuffer json = new StringBuffer();
		json.append("[");
		if (list != null && list.size() > 0) {
			int i = 0;
			for (Object obj : list) {
				json.append(gson.toJson(obj));
				json.append(",");
				i++;
				if (maxrecord == i) {
					break;
				}
			}
			json.setCharAt(json.length() - 1, ']');
		} else {
			json.append("]");
		}
		return json.toString();
	}

	/**
	 * 将json格式转换成list对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}
	
	/**
	 * 将json格式转换成map对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Map<String, String> json2Map(String jsonStr) {
		Map<String, String> map = new HashMap<String, String>();
		// 最外层解析
		JSONObject json = JSONObject.fromObject(jsonStr);
		for (Object k : json.keySet()) {
			Object v = json.get(k);
			map.put(k.toString(), v.toString());
		}
		return map;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object jsonToBean(String jsonStr, Class<?> cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return obj;
	}

	/**
	 * 将json转换成bean对象
	 * 
	 * @param jsonStr
	 * @param cl
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <T> T jsonToBeanDateSerializer(String jsonStr, Class<T> cl,
			final String pattern) {
		Object obj = null;
		gson = new GsonBuilder()
				.registerTypeAdapter(Date.class, new JsonDeserializer<Date>() {
					public Date deserialize(JsonElement json, Type typeOfT,
							JsonDeserializationContext context)
							throws JsonParseException {
						SimpleDateFormat format = new SimpleDateFormat(pattern);
						String dateStr = json.getAsString();
						try {
							return format.parse(dateStr);
						} catch (ParseException e) {
							e.printStackTrace();
						}
						return null;
					}
				}).setDateFormat(pattern).create();
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl);
		}
		return (T) obj;
	}
	
	/**
	 * 从json字符串中获得某个属性的值
	 * 
	 * @param jsonStr json字符串
	 * @param attributeName 属性名称
	 * @return 
	 */
	public static String getJSONAttribute(String jsonStr, String attributeName) {
		String value = "";
		if(StringUtils.isNotEmpty(jsonStr) && StringUtils.isNotEmpty(attributeName)){
			JSONObject json = JSONObject.fromObject(jsonStr);
			value = getJSONAttribute(json, attributeName);
		}
		return value;
	}
	
	/**
	 * 从json对象中获得某个属性的值
	 * 
	 * @param json json对象
	 * @param attributeName 属性名称
	 * @return 
	 */
	public static String getJSONAttribute(JSONObject json, String attributeName) {
		String value = "";
		if(json != null &&  StringUtils.isNotEmpty(attributeName) && json.containsKey(attributeName)){
			value = json.getString(attributeName);
		}
		
		return value;
	}
	
	/**
	 * 将对象 (带timestamp日期)转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String toJsonString(Object bean) {
		String jsonStr = "";
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter()); 
		Gson json = gsonBuilder.create(); 
		if (bean != null) {
			jsonStr = json.toJson(bean);
		}
		return jsonStr;
	}
	
	/**
	 * 将对象 (带timestamp日期)转换成json格式
	 * 
	 * @param ts
	 * @return
	 */
	public static String toJsonString(Object bean,String datePattern) {
		String jsonStr = "";
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter(datePattern));  
		Gson json = gsonBuilder.create(); 
		if (bean != null) {
			jsonStr = json.toJson(bean);
		}
		return jsonStr;
	}
	
	/**
	 * 创建字符串日期转化成timestamp的gson
	 * 
	 * @param datePattern 字符串日期
	 * @return gson对象
	 */
	public static Gson createString2TimestampGson(String datePattern) {
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new TimestampTypeAdapter(datePattern));  
		Gson json = gsonBuilder.create(); 
		if (json == null) {
			json = gson;
		}
		return json;
	}
	
	/**
	 * 创建long时间转化成timestamp的gson
	 * 
	 * @param 
	 * @return gson对象
	 */
	public static Gson createLong2TimestampGson() {
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.registerTypeAdapter(Timestamp.class,new LongTimestampTypeAdapter());  
		Gson json = gsonBuilder.create(); 
		if (json == null) {
			json = gson;
		}
		return json;
	}
}