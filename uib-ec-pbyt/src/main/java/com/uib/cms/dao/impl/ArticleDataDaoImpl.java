package com.uib.cms.dao.impl;

import org.springframework.stereotype.Component;

import com.easypay.core.dao.MyBatisDao;
import com.uib.cms.dao.ArticleDataDao;
import com.uib.cms.entity.ArticleData;

/**
 * CMS 内容管理Dao
 * @author kevin
 *
 */
@Component
public class ArticleDataDaoImpl extends MyBatisDao<ArticleData> implements ArticleDataDao{

	@Override
	public ArticleData getArticleDataById(String id) throws Exception {
		return this.getUnique("getArticleDataById", id);
	}
	
}
