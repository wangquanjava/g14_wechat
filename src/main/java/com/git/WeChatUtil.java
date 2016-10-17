package com.git;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.git.domain.XmlEntity;

public class WeChatUtil {

	public static String getRequestData(HttpServletRequest request) throws IOException {
		ServletInputStream inputStream = request.getInputStream();
		return IOUtils.toString(inputStream);
	}

	public static XmlEntity parseToXmlEntity(String msg) {
		XmlEntity xmlEntity = new XmlEntity();
		try {
			// 得到xml文档对象，并得到元素标签集合
			Document document = DocumentHelper.parseText(msg);
			Element rootElement = document.getRootElement();
			List<Element> elements = rootElement.elements();

			// 遍历xml元素名-值的对应的集合
			for (Element element : elements) {
				String name = element.getName();
				String value = element.getStringValue();

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
}
