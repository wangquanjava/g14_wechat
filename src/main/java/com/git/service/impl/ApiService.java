package com.git.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.domain.HttpResult;

/*
 * 这是用来快捷进行发送get请求和Post请求的类
 * 总结：
 *    1.因为get方式，只用于Restful中的查询，所以状态码很确定，所以get请求就返回数据或者返回null
 *    2.因为Post方式，适用于很多场合(form、Restful)中，所以状态码不确定，所以这里专门新建一个类HttpResult，用作返回数据的封装
 *    
 *    3.这几种方式的对比：
 *       1.Post/Put支持UrlEncodedFormEntity模拟表单的方式进行提交
 *       2.get/delete支持URIBuilder进行url的拼接
 *    
 */
@Service
public class ApiService {
	@Autowired(required = false)
	private CloseableHttpClient httpClient;
	@Autowired(required = false)
	private RequestConfig requestConfig;

	/**
	 * 带参数的get请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doGet(String url, Map<String, Object> map) throws Exception {
		// 把参数拼接到url上
		URIBuilder uriBuilder = new URIBuilder(url);
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				uriBuilder.addParameter(entry.getKey(), entry.getValue().toString());
			}
		}
		// 进行发送，得到数据
		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setConfig(this.requestConfig);
		CloseableHttpResponse response = this.httpClient.execute(httpGet);
		// 返回数据
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity, "utf-8");
		
		HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), content);
		return httpResult;
	}

	public HttpResult doGet(String url) throws Exception {
		return this.doGet(url, null);
	}

	/**
	 * 带参数的post请求
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPost(String url, Map<String, Object> map) throws Exception {
		HttpPost httpPost = new HttpPost(url);
		if (map != null) {
			// 创建一个虚拟的from表单
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 把form给请求对象
			httpPost.setEntity(formEntity);
		}

		CloseableHttpResponse response = httpClient.execute(httpPost);
		String content = EntityUtils.toString(response.getEntity(), "utf-8");

		HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), content);
		return httpResult;
	}

	public HttpResult doPost(String url) throws Exception {
		return this.doPost(url, null);
	}

	/**
	 * 带参数的post请求，请求体存放的是json格式的数据
	 * 
	 * @param url
	 * @param jsonData
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPostJson(String url, String jsonData) throws Exception {
		// 声明httppsot方法
		HttpPost httpPost = new HttpPost(url);
		httpPost.setConfig(this.requestConfig);

		if (StringUtils.isNotBlank(jsonData)) {

			// 创建存放json数据的entity
			// 第一个参数是json格式的数据，第二个参数 是请求的响应类型
			StringEntity entity = new StringEntity(jsonData, ContentType.APPLICATION_JSON);

			// 把实体类放到httppost请求中
			httpPost.setEntity(entity);
		}

		CloseableHttpResponse response = this.httpClient.execute(httpPost);

		return new HttpResult(response.getStatusLine().getStatusCode(),
				EntityUtils.toString(response.getEntity(), "UTF-8"));
	}

	public HttpResult doDelete(String url, Map<String, Object> map) throws Exception {
		// 把参数拼接到url上
		URIBuilder uriBuilder = new URIBuilder(url);
		if (map != null) {
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				String key = entry.getKey();
				Object value = entry.getValue();
				uriBuilder.addParameter(key, value.toString());
			}
		}
		// 发送请求得到数据
		HttpDelete httpDelete = new HttpDelete(uriBuilder.build());
		CloseableHttpResponse response = this.httpClient.execute(httpDelete);
		HttpEntity entity = response.getEntity();

		// 处理数据
		String jsonData = EntityUtils.toString(entity, "utf-8");
		return new HttpResult(response.getStatusLine().getStatusCode(), jsonData);
	}

	public HttpResult doDelete(String url) throws Exception {
		return this.doDelete(url, null);
	}

	/**
	 * doPut方式的提交
	 * 
	 * @param url
	 * @param map
	 * @return
	 * @throws Exception
	 */
	public HttpResult doPut(String url, Map<String, Object> map) throws Exception {
		HttpPut httpPut = new HttpPut(url);
		if (map != null) {
			// 创建一个虚拟的from表单
			List<NameValuePair> parameters = new ArrayList<NameValuePair>(0);
			for (Map.Entry<String, Object> entry : map.entrySet()) {
				parameters.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
			}
			// 构造一个form表单式的实体
			UrlEncodedFormEntity formEntity = new UrlEncodedFormEntity(parameters, "UTF-8");
			// 把form给请求对象
			httpPut.setEntity(formEntity);
		}

		CloseableHttpResponse response = httpClient.execute(httpPut);
		String content = EntityUtils.toString(response.getEntity(), "utf-8");

		HttpResult httpResult = new HttpResult(response.getStatusLine().getStatusCode(), content);
		return httpResult;
	}

	public HttpResult doPut(String url) throws Exception {
		return this.doDelete(url, null);
	}

}
