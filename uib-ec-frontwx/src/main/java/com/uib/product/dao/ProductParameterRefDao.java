/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/easypay_ec">easypay_ec</a> All rights reserved.
 */
package com.uib.product.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.uib.product.entity.ProductParameterRef;

/**
 * 商品参数值DAO接口
 * 
 * @author gaven
 * @version 2015-06-26
 */
public interface ProductParameterRefDao{

	/**
	 * 根据商品Id查询参数Id集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<String> queryParameterIdsByProductId(
			@Param("productId") String productId);

	/**
	 * 根据商品Id查询参数值集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<ProductParameterRef> queryProductParameterRersByProductId(
			@Param("productId") String productId);

}