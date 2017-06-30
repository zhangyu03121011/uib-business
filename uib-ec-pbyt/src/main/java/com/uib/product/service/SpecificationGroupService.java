package com.uib.product.service;

import java.util.List;

import com.uib.product.entity.SpecificationGroup;

public interface SpecificationGroupService {
	
	/**
	 * 根据分类id查询规格组集合
	 * 
	 * @param categoryId
	 *            分类id
	 * @return
	 * @throws Exception
	 */
	public List<SpecificationGroup> querySpecificationGroupsByCategoryId(
			String categoryId) throws Exception;
	
	/**
	 * 根据商品id查询规格组集合
	 * 
	 * @param productId
	 * @return
	 */
	public List<SpecificationGroup> querySpecificationGroupsByProductId(
			String productId) throws Exception;
}
