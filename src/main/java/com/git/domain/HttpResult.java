package com.git.domain;

/**
 * 每次请求的返回对象，存储状态码和返回text
 * @author tdp
 *
 */
public class HttpResult {
    // 状态码
    private Integer code;
    // 响应体
    private String body;
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	@Override
	public String toString() {
		return "HttpResult [code=" + code + ", body=" + body + "]";
	}
	public HttpResult(Integer code, String body) {
		super();
		this.code = code;
		this.body = body;
	}
    
    
}
