package com.git.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.git.WeChatUtil;
import com.git.domain.XmlEntity;

@Controller
public class CheckController {
	private static final Logger logger = Logger.getLogger(CheckController.class);

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
	public ResponseEntity<Void> getMsg(HttpServletRequest request) {
		try {
			
			
			String xmlRequest = WeChatUtil.getRequestData(request);
			XmlEntity xmlEntity = WeChatUtil.parseToXmlEntity(xmlRequest);
			logger.info("微信获得一条消息");
			return ResponseEntity.status(200).body(null);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ResponseEntity.status(500).body(null);
	}
}
