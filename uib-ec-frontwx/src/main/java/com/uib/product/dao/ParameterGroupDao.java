package com.uib.product.dao;

import java.util.List;

import com.uib.product.entity.ParameterGroup;

public interface ParameterGroupDao{
	/**
	 * 根据分类id查询参数组列表
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<ParameterGroup> queryParameterGroupsByCategoryId(
			String categoryId) throws Exception;
}
