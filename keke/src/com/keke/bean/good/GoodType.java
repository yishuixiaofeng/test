package com.keke.bean.good;

import java.io.Serializable;
import java.sql.Timestamp;

import com.keke.util.JsonUtil;
public class GoodType implements Serializable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = 9095651703508063017L;
	/** 唯一主键*/ 
	private int id;
	/** 服务器名称*/ 
	private String f_prent_id;
	/** 服务器ip*/ 
	private String name;
	/** 状态0：可用 -1：不可用*/ 
	private int status;
	/** 创建时间*/ 
	private Timestamp creattime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getF_prent_id() {
		return f_prent_id;
	}
	public void setF_prent_id(String f_prent_id) {
		this.f_prent_id = f_prent_id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public Timestamp getCreattime() {
		return creattime;
	}
	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}
	@Override 
 	public String toString() { 
 		return JsonUtil.toJsonString(this); 
 	} 
 }