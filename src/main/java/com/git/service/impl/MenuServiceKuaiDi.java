package com.git.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.collections.MapUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.git.domain.JuheResponse;
import com.git.domain.KuaiDiEntity;
import com.git.domain.XmlEntity;
import com.git.service.ApiService;
import com.git.service.HttpResult;
import com.git.service.MenuService;

@Service("menuServiceKuaiDi")
public class MenuServiceKuaiDi implements MenuService{
	@Value("${juhe_appkey}")
	String appkey;
	
	@Value("${juhe_kuaidi_url}")
	String url;
	
	@Autowired
	private ApiService apiService;
	
	private Map<String,String> exps = new HashMap<>();
	
	@PostConstruct
	public void init(){
		this.exps.put("顺丰", "sf");
		this.exps.put("申通", "sto");
		this.exps.put("圆通", "yt");
	}
	
	@Override
	public XmlEntity exec(String text,XmlEntity xmlEntity) throws Exception {
		HashMap<String,Object> params = new HashMap<>();
		params.put("key", this.appkey);
		params.put("com", getCom(text));
		params.put("no", getNo(text));
		
		HttpResult httpResult = this.apiService.doPost(this.url, params);
		
		
		if (!httpResult.getCode().equals(200)) {
			return null;
		}
		JuheResponse<KuaiDiEntity> juheResponse = new JuheResponse<>();
		juheResponse.setT(new KuaiDiEntity());
		JuheResponse<KuaiDiEntity> juheResponse2 = JSON.parseObject(httpResult.getBody(), juheResponse.getClass());
		
		XmlEntity xmlEntityResponse = new XmlEntity();
		xmlEntityResponse.setToUserName(xmlEntity.getFromUserName());
		xmlEntityResponse.setFromUserName(xmlEntity.getToUserName());
		xmlEntityResponse.setCreateTime(new Date().getTime() + "");
		xmlEntityResponse.setMsgType("text");
		xmlEntityResponse.setContent(juheResponse2.getT().getCompany());
		
		return xmlEntityResponse;
	}
	/**
	 * 从文本中提取“顺丰”，并返回sf
	 * @param text
	 * @return
	 */
	public String getCom(String text){
		Set<Entry<String,String>> entrySet = this.exps.entrySet();
		
		for (Entry<String, String> entry : entrySet) {
			if (text.contains(entry.getKey())) {
				return entry.getValue();
			}
		}
		return null;
	}
	public String getNo(String text){
		String regex = "\\d*";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(text);
		while(m.find()) {
            if(!"".equals(m.group()))
               return m.group();
        }
		return null;
	}
	@Test
	public void Demo(){
		String no = getCom("顺丰575677355677");
		System.out.println(no);
	}

}
