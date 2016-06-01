package com.keke.bean.good;

import java.io.Serializable;
import java.sql.Timestamp;

import com.keke.util.JsonUtil;
public class Good implements Serializable{ 
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3417521797104450303L;
	/** 唯一主键*/ 
	private int id;
	/** 类型*/ 
	private int type;
	/** 角色名称*/ 
	private String name;
	/** 部门名称*/ 
	private double price;
	/** 角色对应的类*/ 
	private int stock;
	/** 状态0：可用 -1：不可用*/ 
	private String company;
	/** 描述信息*/ 
	private String description;
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
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public int getStock() {
		return stock;
	}
	public void setStock(int stock) {
		this.stock = stock;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
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