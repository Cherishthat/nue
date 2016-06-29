package com.hxgy.nurexcute.dto;

import java.io.Serializable;

public class ArcItmInstructionDTO implements Serializable{
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
private String instr;
private String rowid;
public String getInstr() {
	return instr;
}
public void setInstr(String instr) {
	this.instr = instr;
}
public String getRowid() {
	return rowid;
}
public void setRowid(String rowid) {
	this.rowid = rowid;
}

}
