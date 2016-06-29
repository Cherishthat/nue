package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ExcuteOrderDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4959584821397988803L;
	private String serNo;
	private String bedno;
	private String linkSerNo;
	private String linkOrdID;
	private String ordID;
	private String orderSectionId;
	private String orderSection;
	private String orderDoctor;
	private String orderDate;

	private String orderName;
	private String orderStatus;
	private String orderStatusDesc;
	private String orderBefore;
	private String labNo;
	private String uom;
	private String freq;
	private String phOrdQtyUnit;
	private String instruc;
	private String recSection;
	private String orderExeDate;
	private String exeDate;
	private String exeUserId;
	private String exeUser;
	private String exeSatus;
	private String czSatus;
	private String disposeStatCode;
	private String exeSection;
	private String stopDate;

	private String stopUser;
	private String clStopDate;
	private String clStopTime;
	private String clStopUser;
	private String orderType;
	private String notes;
	private boolean checked;
	
	
	public String getBedno() {
		return bedno;
	}
	public void setBedno(String bedno) {
		this.bedno = bedno;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	public String getPhOrdQtyUnit() {
		return phOrdQtyUnit;
	}
	public void setPhOrdQtyUnit(String phOrdQtyUnit) {
		this.phOrdQtyUnit = phOrdQtyUnit;
	}
	public String getSerNo() {
		return serNo;
	}
	public void setSerNo(String serNo) {
		this.serNo = serNo;
	}
	public String getLinkSerNo() {
		return linkSerNo;
	}
	public void setLinkSerNo(String linkSerNo) {
		this.linkSerNo = linkSerNo;
	}
	public String getLinkOrdID() {
		return linkOrdID;
	}
	public void setLinkOrdID(String linkOrdID) {
		this.linkOrdID = linkOrdID;
	}
	public String getOrdID() {
		return ordID;
	}
	public void setOrdID(String ordID) {
		this.ordID = ordID;
	}
	public String getOrderSectionId() {
		return orderSectionId;
	}
	public void setOrderSectionId(String orderSectionId) {
		this.orderSectionId = orderSectionId;
	}
	public String getOrderSection() {
		return orderSection;
	}
	public void setOrderSection(String orderSection) {
		this.orderSection = orderSection;
	}
	public String getOrderDoctor() {
		return orderDoctor;
	}
	public void setOrderDoctor(String orderDoctor) {
		this.orderDoctor = orderDoctor;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}

	public String getOrderName() {
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getOrderStatusDesc() {
		return orderStatusDesc;
	}
	public void setOrderStatusDesc(String orderStatusDesc) {
		this.orderStatusDesc = orderStatusDesc;
	}
	public String getOrderBefore() {
		return orderBefore;
	}
	public void setOrderBefore(String orderBefore) {
		this.orderBefore = orderBefore;
	}
	public String getLabNo() {
		return labNo;
	}
	public void setLabNo(String labNo) {
		this.labNo = labNo;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getInstruc() {
		return instruc;
	}
	public void setInstruc(String instruc) {
		this.instruc = instruc;
	}
	public String getRecSection() {
		return recSection;
	}
	public void setRecSection(String recSection) {
		this.recSection = recSection;
	}
	public String getOrderExeDate() {
		return orderExeDate;
	}
	public void setOrderExeDate(String orderExeDate) {
		this.orderExeDate = orderExeDate;
	}
	public String getExeDate() {
		return exeDate;
	}
	public void setExeDate(String exeDate) {
		this.exeDate = exeDate;
	}
	public String getExeUserId() {
		return exeUserId;
	}
	public void setExeUserId(String exeUserId) {
		this.exeUserId = exeUserId;
	}
	public String getExeUser() {
		return exeUser;
	}
	public void setExeUser(String exeUser) {
		this.exeUser = exeUser;
	}
	public String getExeSatus() {
		return exeSatus;
	}
	public void setExeSatus(String exeSatus) {
		this.exeSatus = exeSatus;
	}
	public String getCzSatus() {
		return czSatus;
	}
	public void setCzSatus(String czSatus) {
		this.czSatus = czSatus;
	}
	public String getDisposeStatCode() {
		return disposeStatCode;
	}
	public void setDisposeStatCode(String disposeStatCode) {
		this.disposeStatCode = disposeStatCode;
	}
	public String getExeSection() {
		return exeSection;
	}
	public void setExeSection(String exeSection) {
		this.exeSection = exeSection;
	}
	public String getStopDate() {
		return stopDate;
	}
	public void setStopDate(String stopDate) {
		this.stopDate = stopDate;
	}

	public String getStopUser() {
		return stopUser;
	}
	public void setStopUser(String stopUser) {
		this.stopUser = stopUser;
	}
	public String getClStopDate() {
		return clStopDate;
	}
	public void setClStopDate(String clStopDate) {
		this.clStopDate = clStopDate;
	}
	public String getClStopTime() {
		return clStopTime;
	}
	public void setClStopTime(String clStopTime) {
		this.clStopTime = clStopTime;
	}
	public String getClStopUser() {
		return clStopUser;
	}
	public void setClStopUser(String clStopUser) {
		this.clStopUser = clStopUser;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getNotes() {
		return notes;
	}
	public void setNotes(String notes) {
		this.notes = notes;
	}

}
