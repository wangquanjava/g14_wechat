package com.git.controller;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.WeChatUtil;
import com.git.controller.distribute.ServiceDistribute;
import com.git.domain.XmlEntity;
import com.git.service.ExecServiceI;

@Controller
public class MessageController {
	private static final Logger logger = Logger.getLogger(MessageController.class);
	
	@Autowired
	ServiceDistribute serviceDistribute;
	

	/**
	 * 当用户发出一句话时，会请求这个路径，区别于上面，这个是POST方式
	 * 
	 * @param signature
	 *            签名，用来比对是否一致
	 * @param echostr
	 *            成功后需要返回的随机字符串
	 * @param timestamp
	 *            时间戳
	 * @param nonce
	 *            随机数
	 * @return
	 */

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> getMsg(HttpServletRequest request) {
		try {
			//从request中获取xml信息
			String xmlRequest = WeChatUtil.getRequestData(request);
			//把xml封装到entity中
			XmlEntity xmlEntity = WeChatUtil.parseToEntity(xmlRequest);
			logger.error("用户" + xmlEntity.getFromUserName() + "在" + DateFormatUtils.format(new Date(), "HH:mm:ss") + "发来消息:"
					+ xmlEntity.getContent());
			
			// 从调度器中获得当前用户的service，如果为null或者文本是?，就直接赋值菜单显示service
			ExecServiceI execServiceI = ServiceDistribute.getService(xmlEntity.getFromUserName());
			if (execServiceI==null||StringUtils.contains("？?", xmlEntity.getContent())) {
				execServiceI = serviceDistribute.menuServiceImpl;
				ServiceDistribute.setService(xmlEntity.getFromUserName(), execServiceI);
			}
			XmlEntity xmlRes = execServiceI.exec(xmlEntity);
			return ResponseEntity.status(200).body(WeChatUtil.parseToXml(xmlRes));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(200).body(null);
	}
}
