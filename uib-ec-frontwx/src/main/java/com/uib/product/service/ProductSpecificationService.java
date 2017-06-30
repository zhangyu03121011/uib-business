package com.uib.product.service;

import java.util.List;

import com.uib.product.entity.ProductSpecification;

public interface ProductSpecificationService {
	/**
	 * 根据商品id查询规格集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductSpecification> querySpecificationsByProductId(
			String productId);
	
	public List<String> querySpecificationValues(String productId);
}
