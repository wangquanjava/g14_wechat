package com.git.domain.menu;

import com.git.service.ExecServiceI;

/**
 * 菜单类
 * @author tdp
 *
 */
public class Menu {
	private String text;
	private ExecServiceI execServiceI;
	private String mark;
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ExecServiceI getExecServiceI() {
		return execServiceI;
	}
	public void setExecServiceI(ExecServiceI execServiceI) {
		this.execServiceI = execServiceI;
	}
	public Menu(String text, ExecServiceI execServiceI,String mark) {
		super();
		this.text = text;
		this.execServiceI = execServiceI;
		this.mark = mark;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	
	
	
}
