package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmLimitstrDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String maxdurfactor;
private String maxqty;
private String alertstockqty;
public String getMaxdurfactor() {
	return maxdurfactor;
}
public void setMaxdurfactor(String maxdurfactor) {
	this.maxdurfactor = maxdurfactor;
}
public String getMaxqty() {
	return maxqty;
}
public void setMaxqty(String maxqty) {
	this.maxqty = maxqty;
}
public String getAlertstockqty() {
	return alertstockqty;
}
public void setAlertstockqty(String alertstockqty) {
	this.alertstockqty = alertstockqty;
}

}
