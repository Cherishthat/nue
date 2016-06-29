package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmDurationDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String dur;
private String rowid;
private String factor;
public String getDur() {
	return dur;
}
public void setDur(String dur) {
	this.dur = dur;
}
public String getRowid() {
	return rowid;
}
public void setRowid(String rowid) {
	this.rowid = rowid;
}
public String getFactor() {
	return factor;
}
public void setFactor(String factor) {
	this.factor = factor;
}

}
