package com.uib.product.dao;

import java.util.List;

import com.uib.product.entity.Brand;

public interface BrandDao {

	/**
	 * 根据分类ID获取品牌列表
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<Brand> queryBrandByCategoryId(String categoryId)
			throws Exception;

	public Brand getBrand(String id)throws Exception;
}
