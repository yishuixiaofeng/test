package com.keke.bean.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.keke.util.JsonUtil;
public class UserGood implements Serializable{ 
	/**
	 * 
	 */
	private static final long serialVersionUID = -4076537128260713784L;
	/** 唯一主键*/ 
	private int id;
	/** 商品id*/ 
	private int f_good_id;
	/** 商品id*/ 
	private String f_good_name;
	/** 用户id*/ 
	private int f_user_id;
	/** 商品id*/ 
	private String f_user_name;
	/** 类型*/ 
	private int type;
	/** 状态0：可用 -1：不可用*/ 
	private int status;
	/** 创建时间*/ 
	private Timestamp creattime;
	/** 购买数量*/ 
	private int buyCount;
	/** 应付总额*/ 
	private double totalMoney;
	/** 实付金额*/ 
	private double finalMoney;
	/** 描述信息*/ 
	private String description;
	public String getF_good_name() {
		return f_good_name;
	}
	public void setF_good_name(String f_good_name) {
		this.f_good_name = f_good_name;
	}
	public String getF_user_name() {
		return f_user_name;
	}
	public void setF_user_name(String f_user_name) {
		this.f_user_name = f_user_name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getF_good_id() {
		return f_good_id;
	}
	public void setF_good_id(int f_good_id) {
		this.f_good_id = f_good_id;
	}
	public int getF_user_id() {
		return f_user_id;
	}
	public void setF_user_id(int f_user_id) {
		this.f_user_id = f_user_id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
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
	public int getBuyCount() {
		return buyCount;
	}
	public void setBuyCount(int buyCount) {
		this.buyCount = buyCount;
	}
	public double getTotalMoney() {
		return totalMoney;
	}
	public void setTotalMoney(double totalMoney) {
		this.totalMoney = totalMoney;
	}
	public double getFinalMoney() {
		return finalMoney;
	}
	public void setFinalMoney(double finalMoney) {
		this.finalMoney = finalMoney;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override 
 	public String toString() { 
 		return JsonUtil.toJsonString(this); 
 	} 
 }