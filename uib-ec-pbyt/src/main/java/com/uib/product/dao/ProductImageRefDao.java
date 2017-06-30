package com.uib.product.dao;

import java.util.List;

import com.uib.product.entity.ProductImageRef;

public interface ProductImageRefDao {

	/**
	 * 根据商品id查询图片
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductImageRef> queryProductImageRefByProductId(
			String productId) throws Exception;
	
	/**
	 * 查询商品最新的3张图片
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<ProductImageRef> queryLast3ImageById(
			String productId) throws Exception;
}
