package com.git.domain;

public class JuheResponse<T> {
	private Integer resultcode;
	private String reason;
	private T t;
	public Integer getResultcode() {
		return resultcode;
	}
	public void setResultcode(Integer resultcode) {
		this.resultcode = resultcode;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public T getT() {
		return t;
	}
	public void setT(T t) {
		this.t = t;
	}
	
	
}
