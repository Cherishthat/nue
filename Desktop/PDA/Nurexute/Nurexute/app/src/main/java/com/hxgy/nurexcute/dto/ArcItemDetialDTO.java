package com.hxgy.nurexcute.dto;

import java.io.Serializable;
import java.util.List;

public class ArcItemDetialDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String rowid ;
	private String desc ; //医嘱名称
	private String ordertype ;//医嘱类型(R,L)
	private String orderItemCatRowid ;//医嘱子类
	private String inciRowid ; //通用名
	private String phprescType ;//处方类型
	private String billType ;//费别
	private ArcItmPriceDTO price;
	private ArcItemPackqtyDTO packqty;
	private List<ArcItemRevLocDTO> recloc;
	private ArcItmFormstrDTO formstr;
	private List<ArcItemDoseQtyDTO> doseqty;
	private ArcItmFrequenceDTO frequence;
	private ArcItmInstructionDTO instruction;
	private ArcItmDurationDTO duration;
	private ArcItmDefpriorDTO defprior;
	private String stockqty;
	private String iskintest;
	private String insurinfo;
	private ArcItmLimitstrDTO limitstr;
	private String outpriorrecloc;
	private String allergy;
	private String isskintype;
	
	public String getRowid() {
		return rowid;
	}
	public void setRowid(String rowid) {
		this.rowid = rowid;
	}
	public String getIsskintype() {
		return isskintype;
	}
	public void setIsskintype(String isskintype) {
		this.isskintype = isskintype;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public String getOrdertype() {
		return ordertype;
	}
	public void setOrdertype(String ordertype) {
		this.ordertype = ordertype;
	}
	public String getOrderItemCatRowid() {
		return orderItemCatRowid;
	}
	public void setOrderItemCatRowid(String orderItemCatRowid) {
		this.orderItemCatRowid = orderItemCatRowid;
	}
	
	public List<ArcItemDoseQtyDTO> getDoseqty() {
		return doseqty;
	}
	public void setDoseqty(List<ArcItemDoseQtyDTO> doseqty) {
		this.doseqty = doseqty;
	}
	public String getInciRowid() {
		return inciRowid;
	}
	public void setInciRowid(String inciRowid) {
		this.inciRowid = inciRowid;
	}
	public String getPhprescType() {
		return phprescType;
	}
	public void setPhprescType(String phprescType) {
		this.phprescType = phprescType;
	}
	public String getBillType() {
		return billType;
	}
	public void setBillType(String billType) {
		this.billType = billType;
	}
	public ArcItmPriceDTO getPrice() {
		return price;
	}
	public void setPrice(ArcItmPriceDTO price) {
		this.price = price;
	}
	public ArcItemPackqtyDTO getPackqty() {
		return packqty;
	}
	public void setPackqty(ArcItemPackqtyDTO packqty) {
		this.packqty = packqty;
	}
	
	public List<ArcItemRevLocDTO> getRecloc() {
		return recloc;
	}
	public void setRecloc(List<ArcItemRevLocDTO> recloc) {
		this.recloc = recloc;
	}
	public ArcItmFormstrDTO getFormstr() {
		return formstr;
	}
	public void setFormstr(ArcItmFormstrDTO formstr) {
		this.formstr = formstr;
	}
	

	public ArcItmFrequenceDTO getFrequence() {
		return frequence;
	}
	public void setFrequence(ArcItmFrequenceDTO frequence) {
		this.frequence = frequence;
	}
	public ArcItmInstructionDTO getInstruction() {
		return instruction;
	}
	public void setInstruction(ArcItmInstructionDTO instruction) {
		this.instruction = instruction;
	}
	public ArcItmDurationDTO getDuration() {
		return duration;
	}
	public void setDuration(ArcItmDurationDTO duration) {
		this.duration = duration;
	}
	public ArcItmDefpriorDTO getDefprior() {
		return defprior;
	}
	public void setDefprior(ArcItmDefpriorDTO defprior) {
		this.defprior = defprior;
	}
	public String getStockqty() {
		return stockqty;
	}
	public void setStockqty(String stockqty) {
		this.stockqty = stockqty;
	}
	public String getIskintest() {
		return iskintest;
	}
	public void setIskintest(String iskintest) {
		this.iskintest = iskintest;
	}
	public String getInsurinfo() {
		return insurinfo;
	}
	public void setInsurinfo(String insurinfo) {
		this.insurinfo = insurinfo;
	}
	public ArcItmLimitstrDTO getLimitstr() {
		return limitstr;
	}
	public void setLimitstr(ArcItmLimitstrDTO limitstr) {
		this.limitstr = limitstr;
	}
	public String getOutpriorrecloc() {
		return outpriorrecloc;
	}
	public void setOutpriorrecloc(String outpriorrecloc) {
		this.outpriorrecloc = outpriorrecloc;
	}
	public String getAllergy() {
		return allergy;
	}
	public void setAllergy(String allergy) {
		this.allergy = allergy;
	}
	
	
}
