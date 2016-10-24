package com.git.service.impl;

import java.util.Date;

import org.springframework.stereotype.Service;

import com.git.controller.distribute.ServiceDistribute;
import com.git.domain.XmlEntity;
import com.git.domain.menu.ResponseMenu;
import com.git.service.ExecServiceI;

@Service("menuChooseServiceImpl")
public class MenuChooseServiceImpl implements ExecServiceI {

	@Override
	public XmlEntity exec(XmlEntity xmlEntity) throws Exception {
		ResponseMenu responseMenu = ServiceDistribute.menus.get(xmlEntity.getContent());
		ServiceDistribute.setService(xmlEntity.getFromUserName(), responseMenu.getExecServiceI());
		return new XmlEntity(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),new Date().getTime()+"","text",responseMenu.getMark());
	}


}
