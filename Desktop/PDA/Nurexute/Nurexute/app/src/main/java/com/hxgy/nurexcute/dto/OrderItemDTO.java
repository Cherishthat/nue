package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class OrderItemDTO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String orderARCIMRowid; //医嘱  4481||1
	private String orderType; //医嘱 类型 R/l R
	private String orderPriorRowid; //医嘱类型 3
	private String orderStartDate; //医嘱开始日期 16/01/2014
	private String orderStartTime; //医嘱开始时间 11:07
	private String orderPackQty; //数量  2
	private String orderPrice; //单价 122
	private String orderRecDepRowid; //接收科室 573
	private String billtypeRowid; //"" 费别
	private String orderDrugFormRowid; //剂型 PHC_DrgForm 938||1
	private String orderDepProcNote; //备注 ""
	private String orderDoseQty; //单次剂量 10
	private String orderDoseUOMRowid; //剂量单位 26
	private String orderQtySum;//
	private String orderFreqRowid; //频次 18
	private String orderDurRowid; //疗程 1
	private String orderInstrRowid; //用法 6
	private String phprescType; //处方类型 1
	private String orderMasterSeqNo; //关联 ""
	private String orderSeqNo; //序号 1
	private String orderSkinTest; //是否皮试 y
	private String orderPhSpecInstr; //草药用法 ""
	private String orderCoverMainIns; //医保Y
	private String orderActionRowid; //1
	private String orderARCOSRowid; //""
	private String orderEndDate; //""
	private String orderEndTime; //""
	private String orderLabSpecRowid; //""
	private String orderMultiDate; //""
	private String orderNotifyClinician;//急诊 N
	private String orderDIACatRowId;//""
	private String orderInsurCatRowId;//""
	private String orderFirstDayTimes; //""
	private String orderCPWStepItemRowId; //临床路径步骤 undefined
	public String getOrderARCIMRowid() {
		return orderARCIMRowid;
	}
	public void setOrderARCIMRowid(String orderARCIMRowid) {
		this.orderARCIMRowid = orderARCIMRowid;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getOrderPriorRowid() {
		return orderPriorRowid;
	}
	
	public String getBilltypeRowid() {
		return billtypeRowid;
	}
	public void setBilltypeRowid(String billtypeRowid) {
		this.billtypeRowid = billtypeRowid;
	}
	public String getOrderQtySum() {
		return orderQtySum;
	}
	public void setOrderQtySum(String orderQtySum) {
		this.orderQtySum = orderQtySum;
	}
	public void setOrderPriorRowid(String orderPriorRowid) {
		this.orderPriorRowid = orderPriorRowid;
	}
	public String getOrderStartDate() {
		return orderStartDate;
	}
	public void setOrderStartDate(String orderStartDate) {
		this.orderStartDate = orderStartDate;
	}
	public String getOrderStartTime() {
		return orderStartTime;
	}
	public void setOrderStartTime(String orderStartTime) {
		this.orderStartTime = orderStartTime;
	}
	public String getOrderPackQty() {
		return orderPackQty;
	}
	public void setOrderPackQty(String orderPackQty) {
		this.orderPackQty = orderPackQty;
	}
	public String getOrderPrice() {
		return orderPrice;
	}
	public void setOrderPrice(String orderPrice) {
		this.orderPrice = orderPrice;
	}
	public String getOrderRecDepRowid() {
		return orderRecDepRowid;
	}
	public void setOrderRecDepRowid(String orderRecDepRowid) {
		this.orderRecDepRowid = orderRecDepRowid;
	}
	public String getOrderDrugFormRowid() {
		return orderDrugFormRowid;
	}
	public void setOrderDrugFormRowid(String orderDrugFormRowid) {
		this.orderDrugFormRowid = orderDrugFormRowid;
	}
	public String getOrderDepProcNote() {
		return orderDepProcNote;
	}
	public void setOrderDepProcNote(String orderDepProcNote) {
		this.orderDepProcNote = orderDepProcNote;
	}
	public String getOrderDoseQty() {
		return orderDoseQty;
	}
	public void setOrderDoseQty(String orderDoseQty) {
		this.orderDoseQty = orderDoseQty;
	}
	public String getOrderDoseUOMRowid() {
		return orderDoseUOMRowid;
	}
	public void setOrderDoseUOMRowid(String orderDoseUOMRowid) {
		this.orderDoseUOMRowid = orderDoseUOMRowid;
	}
	public String getOrderFreqRowid() {
		return orderFreqRowid;
	}
	public void setOrderFreqRowid(String orderFreqRowid) {
		this.orderFreqRowid = orderFreqRowid;
	}
	public String getOrderDurRowid() {
		return orderDurRowid;
	}
	public void setOrderDurRowid(String orderDurRowid) {
		this.orderDurRowid = orderDurRowid;
	}
	public String getOrderInstrRowid() {
		return orderInstrRowid;
	}
	public void setOrderInstrRowid(String orderInstrRowid) {
		this.orderInstrRowid = orderInstrRowid;
	}
	public String getPhprescType() {
		return phprescType;
	}
	public void setPhprescType(String phprescType) {
		this.phprescType = phprescType;
	}
	public String getOrderMasterSeqNo() {
		return orderMasterSeqNo;
	}
	public void setOrderMasterSeqNo(String orderMasterSeqNo) {
		this.orderMasterSeqNo = orderMasterSeqNo;
	}
	public String getOrderSeqNo() {
		return orderSeqNo;
	}
	public void setOrderSeqNo(String orderSeqNo) {
		this.orderSeqNo = orderSeqNo;
	}
	public String getOrderSkinTest() {
		return orderSkinTest;
	}
	public void setOrderSkinTest(String orderSkinTest) {
		this.orderSkinTest = orderSkinTest;
	}
	public String getOrderPhSpecInstr() {
		return orderPhSpecInstr;
	}
	public void setOrderPhSpecInstr(String orderPhSpecInstr) {
		this.orderPhSpecInstr = orderPhSpecInstr;
	}
	public String getOrderCoverMainIns() {
		return orderCoverMainIns;
	}
	public void setOrderCoverMainIns(String orderCoverMainIns) {
		this.orderCoverMainIns = orderCoverMainIns;
	}
	public String getOrderActionRowid() {
		return orderActionRowid;
	}
	public void setOrderActionRowid(String orderActionRowid) {
		this.orderActionRowid = orderActionRowid;
	}
	public String getOrderARCOSRowid() {
		return orderARCOSRowid;
	}
	public void setOrderARCOSRowid(String orderARCOSRowid) {
		this.orderARCOSRowid = orderARCOSRowid;
	}
	public String getOrderEndDate() {
		return orderEndDate;
	}
	public void setOrderEndDate(String orderEndDate) {
		this.orderEndDate = orderEndDate;
	}
	public String getOrderEndTime() {
		return orderEndTime;
	}
	public void setOrderEndTime(String orderEndTime) {
		this.orderEndTime = orderEndTime;
	}
	public String getOrderLabSpecRowid() {
		return orderLabSpecRowid;
	}
	public void setOrderLabSpecRowid(String orderLabSpecRowid) {
		this.orderLabSpecRowid = orderLabSpecRowid;
	}
	public String getOrderMultiDate() {
		return orderMultiDate;
	}
	public void setOrderMultiDate(String orderMultiDate) {
		this.orderMultiDate = orderMultiDate;
	}
	public String getOrderNotifyClinician() {
		return orderNotifyClinician;
	}
	public void setOrderNotifyClinician(String orderNotifyClinician) {
		this.orderNotifyClinician = orderNotifyClinician;
	}
	public String getOrderDIACatRowId() {
		return orderDIACatRowId;
	}
	public void setOrderDIACatRowId(String orderDIACatRowId) {
		this.orderDIACatRowId = orderDIACatRowId;
	}
	public String getOrderInsurCatRowId() {
		return orderInsurCatRowId;
	}
	public void setOrderInsurCatRowId(String orderInsurCatRowId) {
		this.orderInsurCatRowId = orderInsurCatRowId;
	}
	public String getOrderFirstDayTimes() {
		return orderFirstDayTimes;
	}
	public void setOrderFirstDayTimes(String orderFirstDayTimes) {
		this.orderFirstDayTimes = orderFirstDayTimes;
	}
	public String getOrderCPWStepItemRowId() {
		return orderCPWStepItemRowId;
	}
	public void setOrderCPWStepItemRowId(String orderCPWStepItemRowId) {
		this.orderCPWStepItemRowId = orderCPWStepItemRowId;
	}
	@Override
	public String toString() {
		String split = "^";
		StringBuilder str = new StringBuilder();
		str.append(orderARCIMRowid);
		str.append(split);
		str.append(orderType);
		str.append(split);
		str.append(orderPriorRowid);
		str.append(split);
		str.append(orderStartDate);
		str.append(split);
		str.append(orderStartTime);
		str.append(split);
		str.append(orderPackQty);
		str.append(split);
		str.append(orderPrice);
		str.append(split);
		str.append(orderRecDepRowid);
		str.append(split);
		str.append(billtypeRowid);
		str.append(split);
		str.append(orderDrugFormRowid);
		str.append(split);
		str.append(orderDepProcNote);
		str.append(split);
		str.append(orderDoseQty);
		str.append(split);
		str.append(orderDoseUOMRowid);
		str.append(split);
		str.append(orderQtySum);
		str.append(split);
		str.append(orderFreqRowid);
		str.append(split);
		str.append(orderDurRowid);
		str.append(split);
		str.append(orderInstrRowid);
		str.append(split);
		str.append(phprescType);
		str.append(split);
		str.append(orderMasterSeqNo);
		str.append(split);
		str.append(orderSeqNo);
		str.append(split);
		str.append(orderSkinTest);
		str.append(split);
		str.append(orderPhSpecInstr);
		str.append(split);
		str.append(orderCoverMainIns);
		str.append(split);
		str.append(orderActionRowid);
		str.append(split);
		str.append(orderARCOSRowid);
		str.append(split);
		str.append(orderEndDate);
		str.append(split);
		str.append(orderEndTime);
		str.append(split);
		str.append(orderLabSpecRowid);
		str.append(split);
		str.append(orderMultiDate);
		str.append(split);
		str.append(orderNotifyClinician);
		str.append(split);
		str.append(orderDIACatRowId);
		str.append(split);
		str.append(orderInsurCatRowId);
		str.append(split);
		str.append(orderFirstDayTimes);
		str.append(split);
		str.append(orderCPWStepItemRowId);

		return str.toString();
	}
	
	
	
}
