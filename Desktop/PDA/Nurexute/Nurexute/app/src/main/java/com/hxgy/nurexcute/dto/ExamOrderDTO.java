package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ExamOrderDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String studyNo;
	private String itemName;
	private String itemDate;
	private String itemStatus;
	private String oeOrderDr;
	private String isIll;
	private String locName;
	private String replocDr;
	private String ishasImg;
	private String mediumName;
	private String memo;
	private String otherParam;
	private String ordCat;
	private String yxret;
	private String yxDr;
	public String getStudyNo() {
		return studyNo;
	}
	public void setStudyNo(String studyNo) {
		this.studyNo = studyNo;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemDate() {
		return itemDate;
	}
	public void setItemDate(String itemDate) {
		this.itemDate = itemDate;
	}
	public String getItemStatus() {
		return itemStatus;
	}
	public void setItemStatus(String itemStatus) {
		this.itemStatus = itemStatus;
	}
	public String getOeOrderDr() {
		return oeOrderDr;
	}
	public void setOeOrderDr(String oeOrderDr) {
		this.oeOrderDr = oeOrderDr;
	}
	public String getIsIll() {
		return isIll;
	}
	public void setIsIll(String isIll) {
		this.isIll = isIll;
	}
	public String getLocName() {
		return locName;
	}
	public void setLocName(String locName) {
		this.locName = locName;
	}
	public String getReplocDr() {
		return replocDr;
	}
	public void setReplocDr(String replocDr) {
		this.replocDr = replocDr;
	}
	public String getIshasImg() {
		return ishasImg;
	}
	public void setIshasImg(String ishasImg) {
		this.ishasImg = ishasImg;
	}
	public String getMediumName() {
		return mediumName;
	}
	public void setMediumName(String mediumName) {
		this.mediumName = mediumName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOtherParam() {
		return otherParam;
	}
	public void setOtherParam(String otherParam) {
		this.otherParam = otherParam;
	}
	public String getOrdCat() {
		return ordCat;
	}
	public void setOrdCat(String ordCat) {
		this.ordCat = ordCat;
	}
	public String getYxret() {
		return yxret;
	}
	public void setYxret(String yxret) {
		this.yxret = yxret;
	}
	public String getYxDr() {
		return yxDr;
	}
	public void setYxDr(String yxDr) {
		this.yxDr = yxDr;
	}
}
