package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class OBSItemLineDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String date ;
	
	private String data;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

}
