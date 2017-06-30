package com.uib.product.dao;

import com.uib.product.entity.ProductCommentImg;

public interface ProductCommentImgDao {
	void insert(ProductCommentImg record);

	void insertSelective(ProductCommentImg record);
}