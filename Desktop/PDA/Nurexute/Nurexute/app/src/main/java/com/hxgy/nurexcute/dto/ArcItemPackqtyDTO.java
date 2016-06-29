package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItemPackqtyDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String qty;
private String uom;
private String rowid;
public String getQty() {
	return qty;
}
public void setQty(String qty) {
	this.qty = qty;
}
public String getUom() {
	return uom;
}
public void setUom(String uom) {
	this.uom = uom;
}
public String getRowid() {
	return rowid;
}
public void setRowid(String rowid) {
	this.rowid = rowid;
}

}
