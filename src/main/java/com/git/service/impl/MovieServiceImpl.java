package com.git.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.git.WeChatUtil;
import com.git.domain.Item;
import com.git.domain.NewsEntity;
import com.git.domain.XmlEntity;
import com.git.mapper.ItemMapper;
import com.git.service.ExecServiceI;
import com.github.abel533.entity.Example;

@Service("movieServiceImpl")
public class MovieServiceImpl implements ExecServiceI{
	@Autowired
	ItemMapper itemMapper;
	
	@Override
	public String exec(XmlEntity xmlEntity) throws Exception {
		//查询到相关的item
		Example example = new Example(Item.class);
		example.createCriteria().andLike("Title", "%"+xmlEntity.getContent()+"%");
		List<Item> items = this.itemMapper.selectByExample(example);
		
		//拼接对象
		NewsEntity newsEntity = new NewsEntity();
		newsEntity.setArticles(items);
		newsEntity.setArticleCount(items.size());
		newsEntity.setCreateTime(new Date().getTime()+"");
		newsEntity.setMsgType("news");
		newsEntity.setToUserName(xmlEntity.getFromUserName());
		newsEntity.setFromUserName(xmlEntity.getToUserName());
		
		//转换成String，并返回
		return WeChatUtil.parseNewsEntityToXml(newsEntity);
	}
}
