package com.uib.product.dao.impl;

import com.easypay.core.dao.MyBatisDao;
import com.uib.product.dao.ProductCommentImgDao;
import com.uib.product.entity.ProductCommentImg;

public class ProductCommentImgDaoImpl extends MyBatisDao<ProductCommentImg>implements ProductCommentImgDao {

	public void insert(ProductCommentImg record) {

		super.save("insert", record);
	}

	public void insertSelective(ProductCommentImg record) {

		super.save("insertSelective", record);
	}
}