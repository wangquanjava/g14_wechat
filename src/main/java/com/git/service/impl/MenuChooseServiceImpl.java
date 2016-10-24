package com.git.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.git.controller.distribute.ServiceDistribute;
import com.git.domain.XmlEntity;
import com.git.domain.menu.Menu;
import com.git.service.ExecServiceI;

/**
 * 根据传过来的数字，返回响应提示mark
 * @author wangquan
 *
 */
@Service("menuChooseServiceImpl")
public class MenuChooseServiceImpl implements ExecServiceI {

	@Override
	public XmlEntity exec(XmlEntity xmlEntity) throws Exception {
		Menu menu = ServiceDistribute.menus.get(xmlEntity.getContent());
		ServiceDistribute.setService(xmlEntity.getFromUserName(), menu.getExecServiceI());
		return new XmlEntity(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),new Date().getTime()+"","text",menu.getMark());
	}


}
