package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class GroupLoc implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 5250348638921756901L;
private String locid;
private String locdesc;
private String grpid;
private String grpdesc;

public String getLocid() {
	return locid;
}
public void setLocid(String locid) {
	this.locid = locid;
}
public String getLocdesc() {
	return locdesc;
}
public void setLocdesc(String locdesc) {
	this.locdesc = locdesc;
}
public String getGrpid() {
	return grpid;
}
public void setGrpid(String grpid) {
	this.grpid = grpid;
}
public String getGrpdesc() {
	return grpdesc;
}
public void setGrpdesc(String grpdesc) {
	this.grpdesc = grpdesc;
}
}

