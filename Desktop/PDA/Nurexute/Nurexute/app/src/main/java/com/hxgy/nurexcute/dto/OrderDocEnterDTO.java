package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class OrderDocEnterDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String seqno;
	private String ordertype;

	private BaseDataDTO priority;
	private BaseDataDTO desc;
	private String ordermasterseqno;
	private String orderdoseqty;
	private String orderpackuom;
	private BaseDataDTO orderdoseuom;
	private BaseDataDTO orderfreq;
	private BaseDataDTO orderinstr;
	private BaseDataDTO orderdur;
	private String orderpackqty;
	private BaseDataDTO orderrecdep;
	private BaseDataDTO action;
	private String orderfirstdaytimes;
	private String orderdrugformRowid;
	private String orderprice;
	private String phprescType;
	
	public String getPhprescType() {
		return phprescType;
	}
	public void setPhprescType(String phprescType) {
		this.phprescType = phprescType;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	
	public String getOrderprice() {
		return orderprice;
	}
	public void setOrderprice(String orderprice) {
		this.orderprice = orderprice;
	}
	public String getOrderdrugformRowid() {
		return orderdrugformRowid;
	}
	public void setOrderdrugformRowid(String orderdrugformRowid) {
		this.orderdrugformRowid = orderdrugformRowid;
	}
	public String getOrderfirstdaytimes() {
		return orderfirstdaytimes;
	}
	public void setOrderfirstdaytimes(String orderfirstdaytimes) {
		this.orderfirstdaytimes = orderfirstdaytimes;
	}
	public String getOrderpackuom() {
		return orderpackuom;
	}
	public void setOrderpackuom(String orderpackuom) {
		this.orderpackuom = orderpackuom;
	}
	public String getSeqno() {
		return seqno;
	}
	public void setSeqno(String seqno) {
		this.seqno = seqno;
	}
	public BaseDataDTO getPriority() {
		return priority;
	}
	public void setPriority(BaseDataDTO priority) {
		this.priority = priority;
	}
	public BaseDataDTO getDesc() {
		return desc;
	}
	public void setDesc(BaseDataDTO desc) {
		this.desc = desc;
	}
	public String getOrdermasterseqno() {
		return ordermasterseqno;
	}
	public void setOrdermasterseqno(String ordermasterseqno) {
		this.ordermasterseqno = ordermasterseqno;
	}
	public String getOrderdoseqty() {
		return orderdoseqty;
	}
	public void setOrderdoseqty(String orderdoseqty) {
		this.orderdoseqty = orderdoseqty;
	}
	public BaseDataDTO getOrderdoseuom() {
		return orderdoseuom;
	}
	public void setOrderdoseuom(BaseDataDTO orderdoseuom) {
		this.orderdoseuom = orderdoseuom;
	}
	public BaseDataDTO getOrderfreq() {
		return orderfreq;
	}
	public void setOrderfreq(BaseDataDTO orderfreq) {
		this.orderfreq = orderfreq;
	}
	public BaseDataDTO getOrderinstr() {
		return orderinstr;
	}
	public void setOrderinstr(BaseDataDTO orderinstr) {
		this.orderinstr = orderinstr;
	}
	public BaseDataDTO getOrderdur() {
		return orderdur;
	}
	public void setOrderdur(BaseDataDTO orderdur) {
		this.orderdur = orderdur;
	}
	public String getOrderpackqty() {
		return orderpackqty;
	}
	public void setOrderpackqty(String orderpackqty) {
		this.orderpackqty = orderpackqty;
	}
	public BaseDataDTO getOrderrecdep() {
		return orderrecdep;
	}
	public void setOrderrecdep(BaseDataDTO orderrecdep) {
		this.orderrecdep = orderrecdep;
	}
	public BaseDataDTO getAction() {
		return action;
	}
	public void setAction(BaseDataDTO action) {
		this.action = action;
	}
	
	
}
