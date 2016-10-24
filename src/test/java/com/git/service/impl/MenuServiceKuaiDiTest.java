package com.git.service.impl;

import static org.junit.Assert.*;

import org.junit.Test;

import com.alibaba.fastjson.JSON;
import com.git.domain.JuheResponse;
import com.git.domain.KuaiDiEntity;

public class MenuServiceKuaiDiTest {

	@Test
	public void test() {
		fail("Not yet implemented");
	}
	public static void main(String[] args) {
		String json = "{'resultcode':'200','reason':'成功的返回','result':{'company':'顺丰','com':'sf','no':'575677355677','status':'1','list':[{'datetime':'2013-06-25 10:44:05','remark':'已收件','zone':'台州市'},{'datetime':'2013-06-27 08:51:00','remark':'签收人是：已签收','zone':'西安市/咸阳市'}]},'error_code':0}";
		JuheResponse<KuaiDiEntity> juheResponse = new JuheResponse<>();
		
		JuheResponse<KuaiDiEntity> juheResponse2 = JSON.parseObject(json, juheResponse.getClass());
		System.out.println(juheResponse2);
	}
}
