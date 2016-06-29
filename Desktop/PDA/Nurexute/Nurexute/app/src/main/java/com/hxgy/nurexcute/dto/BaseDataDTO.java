package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class BaseDataDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String rowid ="";
private String desc="";
private String num="";
private String interval="";

public String getInterval() {
	return interval;
}
public void setInterval(String interval) {
	this.interval = interval;
}
public String getNum() {
	return num;
}
public void setNum(String num) {
	this.num = num;
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
}
