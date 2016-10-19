package com.git.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.WeChatUtil;
import com.git.domain.XmlEntity;
import com.git.service.impl.MenuServiceKuaiDi;
import com.git.service.impl.MenuServiceTel;
import com.git.service.impl.MenuServiceTianQi;

@Controller
public class CheckController {
	private static final Logger logger = Logger.getLogger(CheckController.class);
	private List<ResponseMenu> responseMenus = new ArrayList<>();
	@PostConstruct
	public void init(){
		responseMenus.add(new ResponseMenu(1, "快递查询",new MenuServiceKuaiDi()));
		responseMenus.add(new ResponseMenu(2, "天气查询",new MenuServiceTianQi()));
		responseMenus.add(new ResponseMenu(3, "手机号查询",new MenuServiceTel()));
		
		Collections.sort(responseMenus,new Comparator<ResponseMenu>() {
			@Override
			public int compare(ResponseMenu o1, ResponseMenu o2) {
				return o2.getIndex().compareTo(o1.getIndex());
			}
		});
	}

	@Value("${token}")
	private String token;

	/**
	 * 用来验证服务器
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
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<String> check(String signature, String echostr, String timestamp, String nonce,
			HttpServletRequest request) {
		// +token后排序
		String[] tempArr = { timestamp, nonce, token };
		Arrays.sort(tempArr);

		// 拼接
		String tempStr = StringUtils.join(tempArr, "");

		// 加密
		tempStr = DigestUtils.sha1Hex(tempStr);

		if (StringUtils.equals(tempStr, signature)) {
			logger.info("微信验证成功！");
			return ResponseEntity.status(200).body(echostr);
		}
		logger.error("微信验证失败！");
		return ResponseEntity.status(500).body(null);
	}

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
			String xmlRequest = WeChatUtil.getRequestData(request);
			XmlEntity xmlEntity = WeChatUtil.parseToEntity(xmlRequest);
			logger.info("用户"+xmlEntity.getFromUserName()+"在"+DateFormatUtils.format(new Date(), "HH:mm:ss")+"发来消息:"+xmlEntity.getContent());
			
			//拼接菜单
			StringBuffer content = new StringBuffer();
			for (ResponseMenu responseMenu : this.responseMenus) {
				content.append(responseMenu.getIndex()+"."+responseMenu.getText()+"\n");
			}
			
			//拼接返回对象
			XmlEntity xmlEntityResponse = new XmlEntity();
			xmlEntityResponse.setToUserName(xmlEntity.getFromUserName());
			xmlEntityResponse.setFromUserName(xmlEntity.getToUserName());
			xmlEntityResponse.setCreateTime(new Date().getTime()+"");
			xmlEntityResponse.setMsgType("text");
			xmlEntityResponse.setContent(content.toString());
			
			//把对象中不为null的转换为xml
			return ResponseEntity.status(200).body(WeChatUtil.parseToXml(xmlEntityResponse));
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(500).body(null);
	}
}
