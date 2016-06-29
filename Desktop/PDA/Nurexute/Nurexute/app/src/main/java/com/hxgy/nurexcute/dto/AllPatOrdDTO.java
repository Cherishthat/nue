package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class AllPatOrdDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String seqNo;
	private String bedNo;
	private String arcimDes;
	private String execUser;
	private String execDatTim;
	private String dose;
	private String meth;
	private String phrreq;
	private String disposeStatCode;
	
	public String getDisposeStatCode() {
		return disposeStatCode;
	}
	public void setDisposeStatCode(String disposeStatCode) {
		this.disposeStatCode = disposeStatCode;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getArcimDes() {
		return arcimDes;
	}
	public void setArcimDes(String arcimDes) {
		this.arcimDes = arcimDes;
	}
	public String getExecUser() {
		return execUser;
	}
	public void setExecUser(String execUser) {
		this.execUser = execUser;
	}
	public String getExecDatTim() {
		return execDatTim;
	}
	public void setExecDatTim(String execDatTim) {
		this.execDatTim = execDatTim;
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
	public String getPhrreq() {
		return phrreq;
	}
	public void setPhrreq(String phrreq) {
		this.phrreq = phrreq;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	private String priority;
}
