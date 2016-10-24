package com.git.domain.juhe.kuaidi;

import java.util.List;

public class KuaiDiEntity {
	private String company;
	private String com ;
	private String no;
	private List<KuaiDiInfoEntity> list;
	private Integer status;
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getCom() {
		return com;
	}
	public void setCom(String com) {
		this.com = com;
	}
	public String getNo() {
		return no;
	}
	public void setNo(String no) {
		this.no = no;
	}
	public List<KuaiDiInfoEntity> getList() {
		return list;
	}
	public void setList(List<KuaiDiInfoEntity> list) {
		this.list = list;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	
	
}
