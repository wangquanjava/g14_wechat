package com.git;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.git.domain.Item;
import com.git.domain.NewsEntity;
import com.git.domain.XmlEntity;

public class WeChatUtil {

	public static String getRequestData(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		return IOUtils.toString(inputStream);
	}

	/**
	 * 使用反射把xml注入到XmlEntity中
	 * @param msg
	 * @return
	 */
	public static XmlEntity parseToEntity(String msg) {
		XmlEntity xmlEntity = new XmlEntity();
		try {
			// 得到xml文档对象，并得到元素标签集合
			Document document = DocumentHelper.parseText(msg);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();

			// 遍历xml元素名-值的对应的集合
			for (Element element : elements) {
				String name = element.getName();
				String value = formatRequest(element.getStringValue());

				// 执行以xml元素名为名的方法
				Class<? extends XmlEntity> clazz = xmlEntity.getClass();
				Method method = clazz.getDeclaredMethod("set" + name, clazz.getDeclaredField(name).getType());
				method.invoke(xmlEntity, value);
			}
			return xmlEntity;
		} catch (Exception e) {
		}
		return xmlEntity;
	}
	
	/**
	 * 去掉回车、空格 
	 * @param requsetText
	 * @return
	 */
	public static String formatRequest(String requsetText){
		return StringUtils.remove(requsetText, "\n").trim();
	}
	
	/**
	 * 使用反射得到XmlEntity的get方法，拼接xml
	 * @param xmlEntity
	 * @return
	 * @throws Exception
	 */
	public static String parseToXml(XmlEntity xmlEntity) throws Exception{
		String xmlResult = "";
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("<xml>");
		
		//开始遍历方法
		Method[] methods = xmlEntity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			if (method.getName().startsWith("get")) {
				String temp = (String) method.invoke(xmlEntity, null);
				if (temp!=null) {
					String value = "<![CDATA[" + temp + "]]>";
					sb.append("<" + StringUtils.substringAfter(method.getName(), "get") + ">" + value + "</" + StringUtils.substringAfter(method.getName(), "get") + ">");
				}
			}
		}
		sb.append("</xml>");
		xmlResult = sb.toString();
		return xmlResult;
	}
	/**
	 * 专门用来解析封装图文消息的NewsEntity
	 * @param xmlEntity
	 * @return
	 * @throws Exception
	 */
	public static String parseNewsEntityToXml(NewsEntity newsEntity) throws Exception{
		String xmlResult = "";
		StringBuffer sb = new StringBuffer();
		
		
		sb.append("<xml>");
		
		//开始遍历方法
		Method[] methods = newsEntity.getClass().getDeclaredMethods();
		for (Method method : methods) {
			//如果为图文消息字段这里单独处理
			if (StringUtils.equals(method.getName(), "getArticles")) {
				sb.append("<Articles>");
				List<Item> articles = (List<Item>) method.invoke(newsEntity, null);
				for (Item item : articles) {
					sb.append("<item>");
					sb.append("<Title>" + "<![CDATA[" + item.getTitle() + "]]>" + "</Title>");
					sb.append("<Description>" + "<![CDATA[" + item.getDescription() + "]]>" + "</Description>");
					sb.append("<PicUrl>" + "<![CDATA[" + item.getPicUrl() + "]]>" + "</PicUrl>");
					sb.append("<Url>" + "<![CDATA[" + item.getUrl() + "]]>" + "</Url>");
					sb.append("</item>");
				}
				sb.append("</Articles>");
				continue;
			}
			if (method.getName().startsWith("get")) {
				Object temp = method.invoke(newsEntity, null);
				if (temp!=null) {
					String value = "<![CDATA[" + temp + "]]>";
					sb.append("<" + StringUtils.substringAfter(method.getName(), "get") + ">" + value + "</" + StringUtils.substringAfter(method.getName(), "get") + ">");
				}
			}
		}
		sb.append("</xml>");
		xmlResult = sb.toString();
		return xmlResult;
	}
	
}
