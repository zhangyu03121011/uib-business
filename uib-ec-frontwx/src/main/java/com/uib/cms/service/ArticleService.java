package com.uib.cms.service;

import java.util.List;
import java.util.Map;

import com.uib.cms.entity.Article;
import com.uib.cms.entity.ArticleData;


public interface ArticleService {
	
	/**
	 * 根据栏目ID 获取文章
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	List<Article> getCmsArticleByCategoryId(String categoryId) throws Exception;

	/**
	 * 根据栏目No 获取注册协议
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	Map<String,Object> getCmsArticleByCmsCategoryNo(String cmsCategoryNo) throws Exception;

	/**
	 * 根据ID 获取文章内容
	 * @param id
	 * @return
	 * @throws Exception
	 */
	ArticleData getArticleDataById(String id) throws Exception;
	
	/**
	 * 根据栏目No 获取帮助
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	List<Map<String,Object>> getCmsArticlesByCmsCategoryNo(String cmsCategoryNo) throws Exception;

}
