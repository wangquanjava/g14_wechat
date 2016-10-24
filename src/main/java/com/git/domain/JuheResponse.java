package com.git.domain;

public class JuheResponse<T> {
	private Integer resultcode;
	private String reason;
	private T result;
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
	public T getResult() {
		return result;
	}
	public void setResult(T result) {
		this.result = result;
	}
	
	
	
}
