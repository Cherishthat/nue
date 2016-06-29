package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class OBSItemDTO implements Serializable{
private static final long serialVersionUID = -1875175045243033836L;
private String id;
private String desc;
private String uom;
private String value;
public String getId() {
	return id;
}
public String getValue() {
	return value;
}
public void setValue(String value) {
	this.value = value;
}
public void setId(String id) {
	this.id = id;
}
public String getDesc() {
	return desc;
}
public void setDesc(String desc) {
	this.desc = desc;
}
public String getUom() {
	return uom;
}
public void setUom(String uom) {
	this.uom = uom;
}

}
