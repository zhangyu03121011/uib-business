package com.uib.product.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.uib.core.dao.MyBatisDao;
import com.uib.product.dao.ProductCommentDao;
import com.uib.product.entity.ProductComment;

@Component
public class ProductCommentDaoImpl extends MyBatisDao<ProductComment>implements ProductCommentDao {

	public void deleteByPrimaryKey(String id) {
		super.remove("deleteByPrimaryKey", id);
	}

	public void insert(ProductComment record) {
		super.save("insert", record);
	}

	public void insertSelective(ProductComment record) {
		super.save("insertSelective", record);
	}

	public ProductComment selectByPrimaryKey(String id) {

		return super.getUnique("selectByPrimaryKey", id);
	}

	public void updateByPrimaryKeySelective(ProductComment record) {
		super.update("updateByPrimaryKeySelective", record);
	}

	public void updateByPrimaryKey(ProductComment record) {
		super.update("ProductComment", record);
	}

	public List<Map<String, Object>> findById(Map<String, Object> params) {

		return super.getMapPage("findById", params);
	}
	
	public Map<String, Object> findCountGroupByProductId(String productId) {

		return super.getMap("findCountGroupByProductId", productId);
	}
	
	public Map<String, Object> queryComment(Map<String, Object> params) {

		return super.getMap("queryComment", params);
	}
	
	@Override
	public List<Map<String, Object>> queryLast5Comment(Map<String, Object> params) {

		return super.getMapPage("queryLast5Comment", params);
	}

	@Override
	public List<Map<String, Object>> queryCommentByPage(
			Map<String, Object> params) {
		return super.getMapPage("queryCommentByPage", params);
	}

}