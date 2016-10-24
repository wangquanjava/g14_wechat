package com.git.service.impl;

import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.git.WeChatUtil;
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
	@Resource(name = "menuServiceImpl")
	public ExecServiceI menuServiceImpl;
	@Override
	public String exec(XmlEntity xmlEntity) throws Exception {
		//根据传入的数字，得到所选的菜单对象
		Menu menu = ServiceDistribute.menus.get(xmlEntity.getContent());
		//如果没有查到，就再显示选择页面
		if (menu==null) {
			return menuServiceImpl.exec(xmlEntity);
		}
		//根据所选设置该用户的service
		ServiceDistribute.setService(xmlEntity.getFromUserName(), menu.getExecServiceI());
		
		//构建返回mark语并返回
		XmlEntity xmlEntityRes = new XmlEntity(xmlEntity.getFromUserName(),xmlEntity.getToUserName(),new Date().getTime()+"","text",menu.getMark());
		return WeChatUtil.parseToXml(xmlEntityRes);
	}


}
