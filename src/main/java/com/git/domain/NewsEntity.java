package com.git.domain;

import java.util.List;

public class NewsEntity {
	private String ToUserName;
	private String FromUserName;
	private String CreateTime;
	private String MsgType;
	private Integer ArticleCount;
	private List<Item> articles;
	
	
	public String getToUserName() {
		return ToUserName;
	}
	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}
	public String getFromUserName() {
		return FromUserName;
	}
	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}
	public String getCreateTime() {
		return CreateTime;
	}
	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}
	public String getMsgType() {
		return MsgType;
	}
	public void setMsgType(String msgType) {
		MsgType = msgType;
	}
	public Integer getArticleCount() {
		return ArticleCount;
	}
	public void setArticleCount(Integer articleCount) {
		ArticleCount = articleCount;
	}
	public List<Item> getArticles() {
		return articles;
	}
	public void setArticles(List<Item> articles) {
		this.articles = articles;
	}
	
	
}
