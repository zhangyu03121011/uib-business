package com.uib.product.dao;

import java.util.List;

import com.uib.product.entity.PropertyGroup;

public interface PropertyGroupDao {
	/**
	 * 根据分类Id查询属性组列表
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<PropertyGroup> queryPropertyGoupsByCategoryId(String categoryId)
			throws Exception;

	/**
	 * 根据商品id查询属性组
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	public List<PropertyGroup> queryPropertyGroupsByProductId(String productId)
			throws Exception;
}
