package com.uib.product.service;

import java.util.List;

import com.uib.product.entity.ProductImageRef;

public interface ProductImageRefService {

	/**
	 * 根据商品id查询图片
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductImageRef> queryProductImageRefByProductId(
			String productId) throws Exception;
	
	
	public List<ProductImageRef> queryLast3ImageById(
			String productId) throws Exception;
}
