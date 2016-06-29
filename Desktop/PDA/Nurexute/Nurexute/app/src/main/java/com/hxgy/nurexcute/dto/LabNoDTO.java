package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class LabNoDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String arcIMDes;
	private String labEpisodeNo;
	private String stdateTime;
	private String rowId;
	private String code;
	private String labCpt;
	private String labDate;
	private String labTime;
	private String disposeStatCode;
	private String orderStatus;
	
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getDisposeStatCode() {
		return disposeStatCode;
	}
	public void setDisposeStatCode(String disposeStatCode) {
		this.disposeStatCode = disposeStatCode;
	}
	public String getArcIMDes() {
		return arcIMDes;
	}
	public void setArcIMDes(String arcIMDes) {
		this.arcIMDes = arcIMDes;
	}
	public String getLabEpisodeNo() {
		return labEpisodeNo;
	}
	public void setLabEpisodeNo(String labEpisodeNo) {
		this.labEpisodeNo = labEpisodeNo;
	}
	public String getStdateTime() {
		return stdateTime;
	}
	public void setStdateTime(String stdateTime) {
		this.stdateTime = stdateTime;
	}
	public String getRowId() {
		return rowId;
	}
	public void setRowId(String rowId) {
		this.rowId = rowId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getLabCpt() {
		return labCpt;
	}
	public void setLabCpt(String labCpt) {
		this.labCpt = labCpt;
	}
	public String getLabDate() {
		return labDate;
	}
	public void setLabDate(String labDate) {
		this.labDate = labDate;
	}
	public String getLabTime() {
		return labTime;
	}
	public void setLabTime(String labTime) {
		this.labTime = labTime;
	}

}
