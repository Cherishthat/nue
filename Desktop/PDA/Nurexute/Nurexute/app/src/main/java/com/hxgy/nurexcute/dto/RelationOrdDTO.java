package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class RelationOrdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String arcimDes;
	private String seqNo;
	private String excutDateTime;
	private String excutNur;
	private String dose;
	private String meth;
	private String exTime;
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
	public String getArcimDes() {
		return arcimDes;
	}
	public void setArcimDes(String arcimDes) {
		this.arcimDes = arcimDes;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getExcutDateTime() {
		return excutDateTime;
	}
	public void setExcutDateTime(String excutDateTime) {
		this.excutDateTime = excutDateTime;
	}
	public String getExcutNur() {
		return excutNur;
	}
	public void setExcutNur(String excutNur) {
		this.excutNur = excutNur;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getMeth() {
		return meth;
	}
	public void setMeth(String meth) {
		this.meth = meth;
	}
	public String getExTime() {
		return exTime;
	}
	public void setExTime(String exTime) {
		this.exTime = exTime;
	}

	

}
