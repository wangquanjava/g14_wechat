package com.git.controller.distribute;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import com.git.domain.menu.ResponseMenu;
import com.git.service.ExecServiceI;

/**
 * 这个类用来根据用户名存储和取出service
 * 1. 如果用户在这里没有存储，默认返回menuservice，并存储起来
 * @author wang
 *
 */
@Controller
public class ServiceDistribute {
	/**
	 * 所有用户的name-service对应表
	 */
	private static final Map<String, ExecServiceI> userRecord = new HashMap<>();
	/**
	 * 所有菜单的index-service对应表
	 */
	public static final Map<String, ResponseMenu> menus = new HashMap<>();
	
	@Resource(name = "kuaiDiService")
	public ExecServiceI kuaiDiService;
	
	@Resource(name = "telServiceImpl")
	public ExecServiceI telServiceImpl;
	
	@Resource(name = "tianQiServiceImpl")
	public ExecServiceI tianQiServiceImpl;
	
	@Resource(name = "menuServiceImpl")
	public ExecServiceI menuServiceImpl;
	
	@Resource(name = "menuChooseServiceImpl")
	public ExecServiceI menuChooseServiceImpl;
	
	
	@PostConstruct
	public void init() {
		menus.put("1", new ResponseMenu("快递查询", kuaiDiService, "请输入快递公司和快递号"));
	}
	
	/**
	 * 根据username获得service实例，没有返回
	 * @param username
	 * @return
	 */
	public static ExecServiceI getService(String username) {
		ExecServiceI menuService = userRecord.get(username);
		return menuService;
	}
	
	public static void setService(String username,ExecServiceI menuService){
		userRecord.put(username,menuService);
	}
	
}
