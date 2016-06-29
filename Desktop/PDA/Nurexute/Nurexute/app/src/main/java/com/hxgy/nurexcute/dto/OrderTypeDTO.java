package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class OrderTypeDTO implements Serializable{

	private static final long serialVersionUID = -7754753751927862546L;

public String desc;
public String rw;
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getRw() {
	return rw;
}
public void setRw(String rw) {
	this.rw = rw;
}
}
