package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ApplyRTDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prescNo;
	private String desc;
	private String dispQty;
	private String reqQty;
	private String uom;
	private String status;
	private String oedis;
	private String pharowid;
	private String userDept;
	private String ordStartDate;
	private String reason;
	private String reasonDr;
	
	public String getReasonDr() {
		return reasonDr;
	}
	public void setReasonDr(String reasonDr) {
		this.reasonDr = reasonDr;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getPrescNo() {
		return prescNo;
	}
	public void setPrescNo(String prescNo) {
		this.prescNo = prescNo;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getDispQty() {
		return dispQty;
	}
	public void setDispQty(String dispQty) {
		this.dispQty = dispQty;
	}
	public String getReqQty() {
		return reqQty;
	}
	public void setReqQty(String reqQty) {
		this.reqQty = reqQty;
	}
	public String getUom() {
		return uom;
	}
	public void setUom(String uom) {
		this.uom = uom;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOedis() {
		return oedis;
	}
	public void setOedis(String oedis) {
		this.oedis = oedis;
	}
	public String getPharowid() {
		return pharowid;
	}
	public void setPharowid(String pharowid) {
		this.pharowid = pharowid;
	}
	public String getUserDept() {
		return userDept;
	}
	public void setUserDept(String userDept) {
		this.userDept = userDept;
	}
	public String getOrdStartDate() {
		return ordStartDate;
	}
	public void setOrdStartDate(String ordStartDate) {
		this.ordStartDate = ordStartDate;
	}
	
}
