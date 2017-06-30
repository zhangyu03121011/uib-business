package com.uib.product.dao;

import java.util.List;
import java.util.Map;

import com.uib.product.entity.ProductComment;

public interface ProductCommentDao {
	void deleteByPrimaryKey(String id);

	void insert(ProductComment record);

	void insertSelective(ProductComment record);

	ProductComment selectByPrimaryKey(String id);

	void updateByPrimaryKeySelective(ProductComment record);

	void updateByPrimaryKey(ProductComment record);

	// 查询评论数  分页查询
	List<Map<String, Object>> findById(Map<String, Object> params);
	
	// 查询所有好评 \差评\ 中评数量
	Map<String, Object> findCountGroupByProductId(String productId);
	
	//  查看商品评价
	Map<String, Object> queryComment(Map<String, Object> params);
	
//  查看最近5条商品评价
	List<Map<String, Object>> queryLast5Comment(Map<String, Object> params);
	
	// 查询评论数  分页查询
	List<Map<String, Object>> queryCommentByPage(Map<String, Object> params);
}