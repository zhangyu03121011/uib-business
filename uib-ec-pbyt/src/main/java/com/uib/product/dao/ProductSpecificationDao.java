package com.uib.product.dao;

import java.util.List;

import com.uib.product.entity.ProductSpecification;

public interface ProductSpecificationDao {
	/**
	 * 根据商品id查询规格集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductSpecification> querySpecificationsByProductId(
			String productId);
	
	public List<String> selectSpecificationNames(String productId);
}
