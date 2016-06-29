package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmFormstrDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String phform;
private String drugformrowid ;
private String conFac;
private String poisoncode;
public String getPhform() {
	return phform;
}
public void setPhform(String phform) {
	this.phform = phform;
}
public String getDrugformrowid() {
	return drugformrowid;
}
public void setDrugformrowid(String drugformrowid) {
	this.drugformrowid = drugformrowid;
}
public String getConFac() {
	return conFac;
}
public void setConFac(String conFac) {
	this.conFac = conFac;
}
public String getPoisoncode() {
	return poisoncode;
}
public void setPoisoncode(String poisoncode) {
	this.poisoncode = poisoncode;
}

}
