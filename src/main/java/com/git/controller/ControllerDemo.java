package com.git.controller;

import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ControllerDemo {
	@Value("${token}")
	private String token;
	
	/**
	 * 用来验证服务器
	 * @param signature 签名，用来比对是否一致
	 * @param echostr 成功后需要返回的随机字符串
	 * @param timestamp 时间戳
	 * @param nonce 随机数
	 * @return
	 */
	@RequestMapping("/checkServer")
	public ResponseEntity<String> check(String signature,String echostr,String timestamp,String nonce,HttpServletRequest request){
		//+token后排序
		String[] tempArr = {timestamp,nonce,token};
		Arrays.sort(tempArr);
		
		//拼接
		String tempStr = StringUtils.join(tempArr, "");
		
		//加密
		tempStr = DigestUtils.sha1Hex(tempStr);
		
		if (StringUtils.equals(tempStr, signature)) {
			return ResponseEntity.status(200).body(echostr);
		}
		
		return ResponseEntity.status(500).body(null);
	}
}
