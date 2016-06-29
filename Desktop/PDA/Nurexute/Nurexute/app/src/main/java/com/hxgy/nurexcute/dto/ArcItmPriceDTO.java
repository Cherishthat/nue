package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmPriceDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String price;
private String discprice;
private String insprice;
private String patprice;
public String getPrice() {
	return price;
}
public void setPrice(String price) {
	this.price = price;
}
public String getDiscprice() {
	return discprice;
}
public void setDiscprice(String discprice) {
	this.discprice = discprice;
}
public String getInsprice() {
	return insprice;
}
public void setInsprice(String insprice) {
	this.insprice = insprice;
}
public String getPatprice() {
	return patprice;
}
public void setPatprice(String patprice) {
	this.patprice = patprice;
}

}
