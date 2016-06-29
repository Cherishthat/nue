package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmFrequenceDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String freq;
private String rowid;
private String factor;
private String interval;
public String getFreq() {
	return freq;
}
public void setFreq(String freq) {
	this.freq = freq;
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
public String getInterval() {
	return interval;
}
public void setInterval(String interval) {
	this.interval = interval;
}

}
