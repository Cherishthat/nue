package com.hxgy.nurexcute.dto;

import java.io.Serializable;
import java.util.List;

public class OrdEnterBaseDTO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrdEnterBaseItemDTO> priority;
	private List<OrdEnterBaseItemDTO> freq;
	private List<OrdEnterBaseItemDTO> instruc ;
	private List<OrdEnterBaseItemDTO> dur;
	private List<OrdEnterBaseItemDTO> action;
	
	public List<OrdEnterBaseItemDTO> getAction() {
		return action;
	}
	public void setAction(List<OrdEnterBaseItemDTO> action) {
		this.action = action;
	}
	public List<OrdEnterBaseItemDTO> getDur() {
		return dur;
	}
	public void setDur(List<OrdEnterBaseItemDTO> dur) {
		this.dur = dur;
	}
	public List<OrdEnterBaseItemDTO> getPriority() {
		return priority;
	}
	public void setPriority(List<OrdEnterBaseItemDTO> priority) {
		this.priority = priority;
	}
	public List<OrdEnterBaseItemDTO> getFreq() {
		return freq;
	}
	public void setFreq(List<OrdEnterBaseItemDTO> freq) {
		this.freq = freq;
	}
	public List<OrdEnterBaseItemDTO> getInstruc() {
		return instruc;
	}
	public void setInstruc(List<OrdEnterBaseItemDTO> instruc) {
		this.instruc = instruc;
	}
	
	
}
