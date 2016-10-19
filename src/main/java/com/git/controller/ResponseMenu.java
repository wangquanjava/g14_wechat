package com.git.controller;

import com.git.service.MenuService;

public class ResponseMenu {
	private Integer index;
	private String text;
	private MenuService menuService;
	public Integer getIndex() {
		return index;
	}
	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public MenuService getMenuService() {
		return menuService;
	}
	public void setMenuService(MenuService menuService) {
		this.menuService = menuService;
	}
	public ResponseMenu(Integer index, String text, MenuService menuService) {
		this.index = index;
		this.text = text;
		this.menuService = menuService;
	}
	
	
}
