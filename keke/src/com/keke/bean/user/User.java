package com.keke.bean.user;

import java.io.Serializable;
import java.sql.Timestamp;

import com.keke.util.JsonUtil;

public class User implements Serializable {
	private int id;
	private String name;
	private String pwd;
	private int age;
	private String description;
	private Timestamp creattime;
	private int status;
	
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPwd() {
		return pwd;
	}
	public void setPwd(String pwd) {
		this.pwd = pwd;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getDescription() {
		return description;
	}
	
	public Timestamp getCreattime() {
		return creattime;
	}
	public void setCreattime(Timestamp creattime) {
		this.creattime = creattime;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return JsonUtil.toJsonString(this);
	}
	
}
