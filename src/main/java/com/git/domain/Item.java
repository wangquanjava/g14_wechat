package com.git.domain;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "item")
public class Item {
	@Column(name="title")
	private String Title;
	@Column(name="description")
	private String Description;
	@Column(name="pic_url")
	private String PicUrl;
	@Column(name="url")
	private String Url;
	
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public String getPicUrl() {
		return PicUrl;
	}
	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}
	public String getUrl() {
		return Url;
	}
	public void setUrl(String url) {
		Url = url;
	}
	
}
