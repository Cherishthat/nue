package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItemRevLocDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String rowid;
private String desc;
private String defeult;
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
public String getDefeult() {
	return defeult;
}
public void setDefeult(String defeult) {
	this.defeult = defeult;
}

}
