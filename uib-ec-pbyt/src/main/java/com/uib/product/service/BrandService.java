package com.uib.product.service;

import java.util.List;

import com.uib.product.entity.Brand;

public interface BrandService {
	
	/**
	 * 根据分类ID获取品牌列表
	 * 
	 * @param categoryId
	 * @return
	 * @throws Exception
	 */
	public List<Brand> queryBrandByCategoryId(String categoryId)
			throws Exception;

	public Brand get(String id)throws Exception;
}
