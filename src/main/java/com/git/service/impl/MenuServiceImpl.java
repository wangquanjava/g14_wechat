package com.git.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.WeChatUtil;
import com.git.controller.distribute.ServiceDistribute;
import com.git.domain.XmlEntity;
import com.git.domain.menu.Menu;
import com.git.service.ExecServiceI;

@Service("menuServiceImpl")
public class MenuServiceImpl implements ExecServiceI {
	@Resource(name = "menuChooseServiceImpl")
	public ExecServiceI menuChooseServiceImpl;
	
	@Override
	public String exec(XmlEntity xmlEntity) throws Exception {
		// 拼接菜单
		StringBuffer content = new StringBuffer();
		Set<String> keySet = ServiceDistribute.menus.keySet();
		
		for (String index : keySet) {
			Menu responseMenu = ServiceDistribute.menus.get(index);
			content.append(index + "." + responseMenu.getText() + "\n");
		}
		
		//菜单发送回去，并把该用户的serivce切换成menuChooseServiceImpl
		ServiceDistribute.setService(xmlEntity.getFromUserName(), menuChooseServiceImpl);
		
		// 拼接返回对象
		XmlEntity xmlEntityResp = new XmlEntity(xmlEntity.getFromUserName(), xmlEntity.getToUserName(), new Date().getTime()+"", "text", content.toString());
		return WeChatUtil.parseToXml(xmlEntityResp);
	}


}
