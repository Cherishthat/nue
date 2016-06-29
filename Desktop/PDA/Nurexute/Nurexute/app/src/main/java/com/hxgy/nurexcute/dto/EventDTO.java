package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class EventDTO implements Serializable{
	
private static final long serialVersionUID = 1L;
private String desc;
private String date;
private String time;
private String username;
private String rowid;
private Boolean check=false;


public Boolean getCheck() {
	return check;
}
public void setCheck(Boolean check) {
	this.check = check;
}
public String getRowid() {
	return rowid;
}
public void setRowid(String rowid) {
	this.rowid = rowid;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getDate() {
	return date;
}
public void setDate(String date) {
	this.date = date;
}
public String getTime() {
	return time;
}
public void setTime(String time) {
	this.time = time;
}
public String getUsername() {
	return username;
}
public void setUsername(String username) {
	this.username = username;
}

}
