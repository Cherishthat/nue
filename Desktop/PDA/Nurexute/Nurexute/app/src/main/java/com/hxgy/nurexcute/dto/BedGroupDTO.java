package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class BedGroupDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String desc;
private String code ;
private String rowid;
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getCode() {
	return code;
}
public void setCode(String code) {
	this.code = code;
}
public String getRowid() {
	return rowid;
}
public void setRowid(String rowid) {
	this.rowid = rowid;
}

}
