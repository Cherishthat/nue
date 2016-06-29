package com.hxgy.nurexcute.dto;

import java.util.List;

public class User {

	private String uid;
	private String name;
	private String hospId;
	public String getHospId() {
		return hospId;
	}
	public void setHospId(String hospId) {
		this.hospId = hospId;
	}
	private List<GroupLoc> list;
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<GroupLoc> getList() {
		return list;
	}
	public void setList(List<GroupLoc> list) {
		this.list = list;
	}
	
	
}
