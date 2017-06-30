package com.uib.cms.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.uib.cms.dao.ArticleDao;
import com.uib.cms.dao.ArticleDataDao;
import com.uib.cms.entity.Article;
import com.uib.cms.entity.ArticleData;
import com.uib.cms.service.ArticleService;


@Component
public class ArticleServiceImpl implements ArticleService {

	@Autowired
	private ArticleDao articleDao;
	
	@Autowired
	private ArticleDataDao articleDataDao;
	
	@Override
	public List<Article> getCmsArticleByCategoryId(String categoryId) throws Exception {
		return articleDao.getCmsArticleByCategoryId(categoryId);
	}

	@Override
	public Map<String,Object> getCmsArticleByCmsCategoryNo(String cmsCategoryNo) throws Exception {
		return articleDao.getCmsArticleByCmsCategoryNo(cmsCategoryNo);
	}
	@Override
	public ArticleData getArticleDataById(String id) throws Exception {
		return articleDataDao.getArticleDataById(id);
	}

	@Override
	public List<Map<String,Object>> getCmsArticlesByCmsCategoryNo(String cmsCategoryNo) throws Exception {
		// TODO Auto-generated method stub
		return articleDao.getCmsArticlesByCmsCategoryNo(cmsCategoryNo);
	}

}
