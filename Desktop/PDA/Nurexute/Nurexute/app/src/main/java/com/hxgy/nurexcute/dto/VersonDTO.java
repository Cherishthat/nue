package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class VersonDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String versonCode;
private String apkName;
public String getVersonCode() {
	return versonCode;
}
public void setVersonCode(String versonCode) {
	this.versonCode = versonCode;
}
public String getApkName() {
	return apkName;
}
public void setApkName(String apkName) {
	this.apkName = apkName;
}

}
