package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItemDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String arcDesc;
	private String rowid;
	private String subCategoryDesc;
	private String price;
	private String billUOM;
	private String stockQty;
	private String packedQty;
	private String genericName;
	private String bxType;
	public String getArcDesc() {
		return arcDesc;
	}
	public void setArcDesc(String arcDesc) {
		this.arcDesc = arcDesc;
	}
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getSubCategoryDesc() {
		return subCategoryDesc;
	}
	public void setSubCategoryDesc(String subCategoryDesc) {
		this.subCategoryDesc = subCategoryDesc;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getBillUOM() {
		return billUOM;
	}
	public void setBillUOM(String billUOM) {
		this.billUOM = billUOM;
	}
	public String getStockQty() {
		return stockQty;
	}
	public void setStockQty(String stockQty) {
		this.stockQty = stockQty;
	}
	public String getPackedQty() {
		return packedQty;
	}
	public void setPackedQty(String packedQty) {
		this.packedQty = packedQty;
	}
	public String getGenericName() {
		return genericName;
	}
	public void setGenericName(String genericName) {
		this.genericName = genericName;
	}
	public String getBxType() {
		return bxType;
	}
	public void setBxType(String bxType) {
		this.bxType = bxType;
	}
	
	
}
