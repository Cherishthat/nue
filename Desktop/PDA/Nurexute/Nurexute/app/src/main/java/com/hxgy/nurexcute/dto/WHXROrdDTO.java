package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class WHXROrdDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
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
	public String getPhfreq() {
		return phfreq;
	}
	public void setPhfreq(String phfreq) {
		this.phfreq = phfreq;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	private String seqNo;
	private String arcimDes;
	private String execUser;
	private String execDatTim;
	private String dose;
	private String meth;
	private String phfreq;
	private String priority;
	private String disposeStatCode;
	public String getDisposeStatCode() {
		return disposeStatCode;
	}
	public void setDisposeStatCode(String disposeStatCode) {
		this.disposeStatCode = disposeStatCode;
	}
}
