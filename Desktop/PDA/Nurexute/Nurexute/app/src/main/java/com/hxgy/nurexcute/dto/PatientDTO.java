package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class PatientDTO implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -5110862525824985843L;
	private String patId;
	private String patNo;
	private String bedNo;
	private String name;
	private String sex;
	private String sexId;
	private String age;
	private String admDoc;
	private String admReason;
	private String ctlocDesc;
	private String dia;
	private String adm;
	private String bedGroup;
	
	private String imgname;
	

	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public String getBedGroup() {
		return bedGroup;
	}
	public void setBedGroup(String bedGroup) {
		this.bedGroup = bedGroup;
	}
	public String getAdm() {
		return adm;
	}
	public void setAdm(String adm) {
		this.adm = adm;
	}
	public String getAdmDoc() {
		return admDoc;
	}
	public void setAdmDoc(String admDoc) {
		this.admDoc = admDoc;
	}
	public String getAdmReason() {
		return admReason;
	}
	public void setAdmReason(String admReason) {
		this.admReason = admReason;
	}
	public String getCtlocDesc() {
		return ctlocDesc;
	}
	public void setCtlocDesc(String ctlocDesc) {
		this.ctlocDesc = ctlocDesc;
	}
	public String getDia() {
		return dia;
	}
	public void setDia(String dia) {
		this.dia = dia;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getSexId() {
		return sexId;
	}
	public void setSexId(String sexId) {
		this.sexId = sexId;
	}
	public String getPatId() {
		return patId;
	}
	public void setPatId(String patId) {
		this.patId = patId;
	}
	public String getPatNo() {
		return patNo;
	}
	public void setPatNo(String patNo) {
		this.patNo = patNo;
	}
	public String getBedNo() {
		return bedNo;
	}
	public void setBedNo(String bedNo) {
		this.bedNo = bedNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}


}
