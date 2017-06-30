package com.uib.cms.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.cms.dao.ArticleDao;
import com.uib.cms.entity.Article;

@Component
public class ArticleDaoImpl extends MyBatisDao<Article> implements ArticleDao {
	
	
	@Override
	public List<Article> getCmsArticleByCategoryId(String categoryId) throws Exception {
		return this.get("getCmsArticleByCategoryId", categoryId);
	}
	@Override
	public  Map<String,Object>  getCmsArticleByCmsCategoryNo(String cmsCategoryNo) throws Exception {
		return super.getMap("getCmsArticleByCmsCategoryNo",  cmsCategoryNo);
	}
	
	public List<Map<String,Object>> getCmsArticlesByCmsCategoryNo(String cmsCategoryNo) throws Exception{
		return this.getList("getCmsArticlesByCmsCategoryNo", cmsCategoryNo);
	};


}
