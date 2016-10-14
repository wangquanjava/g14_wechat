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

@Controller
public class MessageController {
	private static final Logger logger = Logger.getLogger(MessageController.class);
	
	@Value("${token}")
	private String token;
	
	@RequestMapping("/message")
	public ResponseEntity<String> check(String signature,String echostr,String timestamp,String nonce,HttpServletRequest request){
		//+token后排序
		String[] tempArr = {timestamp,nonce,token};
		Arrays.sort(tempArr);
		
		//拼接
		String tempStr = StringUtils.join(tempArr, "");
		
		//加密
		tempStr = DigestUtils.sha1Hex(tempStr);
		
		if (StringUtils.equals(tempStr, signature)) {
			logger.info("微信验证成功！");
			return ResponseEntity.status(200).body(echostr);
		}
		logger.error("微信验证失败！");
		return ResponseEntity.status(500).body(null);
	}
}
