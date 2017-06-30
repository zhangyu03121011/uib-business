package com.uib.cms.dao;

import com.uib.cms.entity.ArticleData;

public interface ArticleDataDao {
	
	/**
	 * 根据ID 获取文章内容
	 * @return
	 * @throws Exception
	 */
	ArticleData getArticleDataById(String id) throws Exception;
	
}
